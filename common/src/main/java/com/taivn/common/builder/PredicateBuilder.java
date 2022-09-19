package com.taivn.common.builder;

/**
 * @author Tai VN
 * @date 9/19/2022
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;

import com.taivn.common.builder.ArrayPredicate.ArrayComparisonOperator;
import com.taivn.common.builder.bean.Condition;
import com.taivn.common.builder.bean.SearchCondition;
import com.taivn.common.constant.DataType;
import com.taivn.common.constant.Operator;
import com.taivn.common.logging.InjectLog;
import com.taivn.common.util.CollectionUtil;
import com.taivn.common.util.DateTimeUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Constant log.
 */
@Slf4j

/**
 * Instantiates a new predicate builder.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PredicateBuilder {

    /**
     * Builds the.
     *
     * @param <T>             the generic type
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param searchCondition the search condition
     * @return the list
     */
    @InjectLog(logParams = false)
    public static <T> List<Predicate> build(Root<T> root, CriteriaBuilder criteriaBuilder,
                                            SearchCondition searchCondition) {
        List<Predicate> predicates = new ArrayList<>(searchCondition.getConditions().size());

        log.debug("build - num_conditions=[{}]", searchCondition.getConditions().size());

        // Predicate for conditions without join
        List<Condition> withoutJoinConditions = filterConditionsWithoutJoin(searchCondition);
        if (!CollectionUtil.isNullOrEmpty(withoutJoinConditions)) {
            buildPredicateWithConditions(root, criteriaBuilder, withoutJoinConditions, predicates);
        }

        // Predicate for conditions with join
        List<Condition> withJoinConditions = filterConditionsWithJoin(searchCondition);

        if (!CollectionUtil.isNullOrEmpty(withJoinConditions)) {
            Map<String, List<Condition>> groupJoinConditions = groupConditionsByJoinTable(withJoinConditions);

            groupJoinConditions.forEach((k, v) -> {
                List<Condition> conditions = v;
                String joinTable = k;
                String joinType = v.get(0).getJoinType();
                Join<?, ?> rootAfterJoin = createTableJoins(root, joinTable, joinType);
                buildPredicateWithConditions(rootAfterJoin, criteriaBuilder, conditions, predicates);
            });

        }

        return predicates;
    }

    /**
     * Builds the predicate with conditions.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param conditionList   the condition list
     * @param predicates      the predicates
     */
    private static void buildPredicateWithConditions(From<?, ?> root, CriteriaBuilder criteriaBuilder,
                                                     List<Condition> conditionList, List<Predicate> predicates) {

        for (Condition condition : conditionList) {
            Predicate predicate = null;
            String key = condition.getKey();
            Object value = condition.getValue();
            String dataType = condition.getType();
            String operator = condition.getOperator();
            String joinTable = condition.getJoinTable();
            String joinType = condition.getJoinType();
            log.debug("build - key=[{}], value=[{}], dataType=[{}], operator=[{}], joinTable=[{}], joinType=[{}]", key,
                    value, dataType, operator, joinTable, joinType);

            switch (operator) {
                case Operator.EQUAL_IN:
                    predicate = buildEqualIn(root, criteriaBuilder, key, value, dataType);
                    break;

                case Operator.NOT_EQUAL_IN:
                    predicate = buildNotEqualIn(root, criteriaBuilder, key, value);
                    break;

                case Operator.LIKE_IN:
                    predicate = buildLikeIn(root, criteriaBuilder, key, value);
                    break;

                case Operator.CONTAIN_ANY:
                    predicate = buildContainAny(root, criteriaBuilder, key, value);
                    break;

                case Operator.EQUAL:
                    predicate = buildEqual(root, criteriaBuilder, key, value, dataType);
                    break;

                case Operator.NOT_EQUAL:
                    predicate = buildNotEqual(root, criteriaBuilder, key, value);
                    break;

                case Operator.LIKE:
                    predicate = buildLike(root, criteriaBuilder, key, value);
                    break;

                case Operator.NOT_LIKE:
                    predicate = buildNotLike(root, criteriaBuilder, key, value);
                    break;

                case Operator.LESS_THAN:
                    predicate = buildLessThan(root, criteriaBuilder, key, value, dataType);
                    break;

                case Operator.LESS_OR_EQUAL:
                    predicate = buildLessOrEqual(root, criteriaBuilder, key, value, dataType);
                    break;

                case Operator.GREATER_THAN:
                    predicate = buildGreaterThan(root, criteriaBuilder, key, value, dataType);
                    break;

                case Operator.GREATER_OR_EQUAL:
                    predicate = buildGreaterOrEqual(root, criteriaBuilder, key, value, dataType);
                    break;

                case Operator.IS_NULL:
                    predicate = buildIsNull(root, criteriaBuilder, key);
                    break;

                case Operator.IS_NOT_NULL:
                    predicate = buildIsNotNull(root, criteriaBuilder, key);
                    break;

                case Operator.IN_ARRAY:
                    predicate = buildInArray(root, criteriaBuilder, key, value);
                    break;

                default:
            }

            if (predicate != null) {
                predicates.add(predicate);
            }
        }
    }

    /**
     * Filter conditions without join.
     *
     * @param searchCondition the search condition
     * @return the list
     */
    private static List<Condition> filterConditionsWithoutJoin(SearchCondition searchCondition) {
        return searchCondition.getConditions().stream().filter(n -> StringUtils.isBlank(n.getJoinTable()))
                .collect(Collectors.toList());
    }

    /**
     * Filter conditions with join.
     *
     * @param searchCondition the search condition
     * @return the list
     */
    private static List<Condition> filterConditionsWithJoin(SearchCondition searchCondition) {
        return searchCondition.getConditions().stream().filter(n -> !StringUtils.isBlank(n.getJoinTable()))
                .collect(Collectors.toList());
    }

    /**
     * Group conditions by join table.
     *
     * @param conditions the conditions
     * @return the map
     */
    private static Map<String, List<Condition>> groupConditionsByJoinTable(List<Condition> conditions) {
        return conditions.stream().collect(Collectors.groupingBy(n -> n.getJoinTable()));
    }

    /**
     * Builds the count greater or equal.
     *
     * @param <T>             the generic type
     * @param <TJoin>         the generic type
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param criteriaQuery   the criteria query
     * @param searchCondition the search condition
     * @param condition       the condition
     * @param typeofTJoin     the typeof T join
     * @return the predicate
     */
    @InjectLog(logParams = false)
    public static <T, TJoin> Predicate buildCountGreaterOrEqual(Path<T> root, CriteriaBuilder criteriaBuilder,
                                                                CommonAbstractCriteria criteriaQuery, SearchCondition searchCondition, Condition condition,
                                                                Class<TJoin> typeofTJoin) {

        String key = condition.getKey();
        Object value = condition.getValue();
        String dataType = condition.getType();
        String operator = condition.getOperator();

        log.debug("buildCountGreaterOrEqual - key=[{}], value=[{}], dataType=[{}], operator=[{}]", key, value, dataType,
                operator);

        Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
        Root<TJoin> rootCount = subQuery.from(typeofTJoin);
        subQuery.where(criteriaBuilder.equal(rootCount.get(key), root.get(key)));

        return criteriaBuilder.ge(subQuery.select(criteriaBuilder.count(rootCount)), (Number) value);
    }

    /**
     * Builds the equal.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildEqual(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value,
                                        String dataType) {
        if (dataType.equals(DataType.DATE)) {
            return criteriaBuilder.equal(root.get(key).as(Date.class), DateTimeUtil.toDate(String.valueOf(value)));
        }

        return criteriaBuilder.equal(root.get(key), value);
    }

    /**
     * Builds the not equal.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildNotEqual(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value) {
        return criteriaBuilder.or(criteriaBuilder.notEqual(root.get(key), value),
                buildIsNull(root, criteriaBuilder, key));
    }

    /**
     * Builds the like.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildLike(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value) {
        return criteriaBuilder.like(criteriaBuilder.upper(root.get(key)), "%" + value.toString().toUpperCase() + "%");
    }

    /**
     * Builds the not like.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildNotLike(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value) {
        return criteriaBuilder.or(criteriaBuilder.notLike(root.get(key), "%" + value.toString().toUpperCase() + "%"),
                buildIsNull(root, criteriaBuilder, key));
    }

    /**
     * Builds the less than.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @param dataType        the data type
     * @return the predicate
     */
    private static Predicate buildLessThan(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value,
                                           String dataType) {
        Predicate predicate = null;

        switch (dataType) {
            case DataType.NUMBER:
                predicate = criteriaBuilder.lt(root.get(key), (Number) value);
                break;

            case DataType.DATE:
                predicate = criteriaBuilder.lessThan(root.get(key).as(Date.class), DateTimeUtil.toDate(value.toString()));
                break;

            case DataType.DATETIME:
                predicate = criteriaBuilder.lessThan(root.get(key).as(Date.class),
                        DateTimeUtil.toDateTime(value.toString()));
                break;

            default:
                break;
        }

        return predicate;
    }

    /**
     * Builds the less or equal.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @param dataType        the data type
     * @return the predicate
     */
    private static Predicate buildLessOrEqual(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key,
                                              Object value, String dataType) {
        Predicate predicate = null;

        switch (dataType) {
            case DataType.NUMBER:
                predicate = criteriaBuilder.le(root.get(key), (Number) value);
                break;

            case DataType.DATE:
                predicate = criteriaBuilder.lessThanOrEqualTo(root.get(key).as(Date.class),
                        DateTimeUtil.toDate(value.toString()));
                break;

            case DataType.DATETIME:
                predicate = criteriaBuilder.lessThanOrEqualTo(root.get(key).as(Date.class),
                        DateTimeUtil.toDateTime(value.toString()));
                break;

            default:
                break;
        }

        return predicate;
    }

    /**
     * Builds the greater than.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @param dataType        the data type
     * @return the predicate
     */
    private static Predicate buildGreaterThan(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key,
                                              Object value, String dataType) {
        Predicate predicate = null;

        switch (dataType) {
            case DataType.NUMBER:
                predicate = criteriaBuilder.gt(root.get(key), (Number) value);
                break;

            case DataType.DATE:
                predicate = criteriaBuilder.greaterThan(root.get(key).as(Date.class),
                        DateTimeUtil.toDate(value.toString()));
                break;

            case DataType.DATETIME:
                predicate = criteriaBuilder.greaterThan(root.get(key).as(Date.class),
                        DateTimeUtil.toDateTime(value.toString()));
                break;

            default:
                break;
        }

        return predicate;
    }

    /**
     * Builds the greater or equal.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @param dataType        the data type
     * @return the predicate
     */
    private static Predicate buildGreaterOrEqual(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key,
                                                 Object value, String dataType) {
        Predicate predicate = null;

        switch (dataType) {
            case DataType.NUMBER:
                predicate = criteriaBuilder.ge(root.get(key), (Number) value);
                break;
            case DataType.DATE:
                predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(key).as(Date.class),
                        DateTimeUtil.toDate(value.toString()));
                break;

            case DataType.DATETIME:
                predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(key).as(Date.class),
                        DateTimeUtil.toDateTime(value.toString()));
                break;

            default:
                break;
        }
        return predicate;
    }

    /**
     * Builds the equal in.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildEqualIn(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value,
                                          String dataType) {
        String[] valueItemList = value.toString().split(",");
        Predicate[] predicateInList = new Predicate[valueItemList.length];

        for (int i = 0; i < valueItemList.length; i++) {
            String valueItem = valueItemList[i].trim();
            Predicate predicateIn = null;

            if (DataType.NUMBER.equalsIgnoreCase(dataType)) {
                predicateIn = criteriaBuilder.equal(root.get(key).as(Integer.class), Integer.valueOf(valueItem));
            } else {
                predicateIn = criteriaBuilder.equal(root.get(key), valueItem);
            }

            predicateInList[i] = predicateIn;
        }

        return criteriaBuilder.or(predicateInList);
    }

    /**
     * Builds the not equal in.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildNotEqualIn(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key,
                                             Object value) {

        String[] valueItemList = value.toString().split(",");
        Predicate[] predicateInList = new Predicate[valueItemList.length];

        for (int i = 0; i < valueItemList.length; i++) {
            String valueItem = valueItemList[i].trim();
            Predicate predicateIn = criteriaBuilder.notEqual(root.get(key), valueItem);
            predicateInList[i] = predicateIn;
        }

        return criteriaBuilder.or(criteriaBuilder.and(predicateInList), buildIsNull(root, criteriaBuilder, key));
    }

    /**
     * Builds the like in.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildLikeIn(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value) {
        String[] valueItemList = value.toString().split(",");
        Predicate[] predicateInList = new Predicate[valueItemList.length];

        for (int i = 0; i < valueItemList.length; i++) {
            String valueItem = valueItemList[i].trim();
            Predicate predicateIn = criteriaBuilder.like(root.get(key), "%" + valueItem + "%");
            predicateInList[i] = predicateIn;
        }

        return criteriaBuilder.or(predicateInList);
    }

    /**
     * Builds the like in.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildContainAny(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key,
                                             Object value) {
        String[] valueItemInList = value.toString().replaceAll("\\[|\\]", "").split(",");
        Predicate[] predicateInList = new Predicate[valueItemInList.length];

        for (int i = 0; i < valueItemInList.length; i++) {
            String valueItem = valueItemInList[i].trim();
            Predicate predicateIn = criteriaBuilder.or(criteriaBuilder.like(root.get(key), "\\[" + valueItem + ",%"),
                    criteriaBuilder.like(root.get(key), "%, " + valueItem + "\\]"),
                    criteriaBuilder.like(root.get(key), "%, " + valueItem + ",%"),
                    criteriaBuilder.like(root.get(key), "\\[" + valueItem + "\\]"));
            predicateInList[i] = predicateIn;
        }

        return criteriaBuilder.or(predicateInList);
    }

    /**
     * Builds the in array.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @param value           the value
     * @return the predicate
     */
    private static Predicate buildInArray(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key, Object value) {
        return new ArrayPredicate((CriteriaBuilderImpl) criteriaBuilder, ArrayComparisonOperator.CONTAINS, value,
                root.get(key));
    }

    /**
     * Builds the is null.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @return the predicate
     */
    private static Predicate buildIsNull(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key) {
        return criteriaBuilder.isNull(root.get(key));
    }

    /**
     * Builds the is not null.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param key             the key
     * @return the predicate
     */
    private static Predicate buildIsNotNull(From<?, ?> root, CriteriaBuilder criteriaBuilder, String key) {
        return criteriaBuilder.isNotNull(root.get(key));
    }

    /**
     * Gets the join type val.
     *
     * @param joinType the join type
     * @return the join type val
     */
    private static JoinType getJoinTypeVal(String joinType) {
        JoinType joinTypeVal = JoinType.INNER;

        if ("left".equals(joinType)) {
            joinTypeVal = JoinType.LEFT;
        } else if ("right".equals(joinType)) {
            joinTypeVal = JoinType.RIGHT;
        }

        return joinTypeVal;
    }

    /**
     * Creates the table joins.
     *
     * @param <T>       the generic type
     * @param root      the root
     * @param joinTable the join table
     * @param joinType  the join type
     * @return the join
     */
    private static <T> Join<?, ?> createTableJoins(Root<T> root, String joinTable, String joinType) {
        Join<?, ?> rootAfterJoin;
        String[] joinTables = joinTable.split(Pattern.quote("."));

        log.debug("createTableJoins - num_tables_join: [{}]", joinTables.length);

        if (joinTables.length > 0) {
            rootAfterJoin = root.join(joinTables[0], getJoinTypeVal(joinType));

            for (int j = 1; j < joinTables.length; j++) {
                rootAfterJoin = rootAfterJoin.join(joinTables[j], getJoinTypeVal(joinType));
            }
        } else {
            rootAfterJoin = root.join(joinTable, getJoinTypeVal(joinType));
        }

        return rootAfterJoin;
    }
}
