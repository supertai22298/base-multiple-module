package com.taivn.common.base.repository;

import com.taivn.common.builder.PredicateBuilder;
import com.taivn.common.builder.bean.SearchCondition;
import com.taivn.common.builder.bean.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Tai VN
 * @date 9/18/2022
 */
@NoRepositoryBean
@Transactional
public interface BaseRepository<T, TId> extends JpaRepository<T, TId>, PagingAndSortingRepository<T, TId>, JpaSpecificationExecutor<T>, QueryByExampleExecutor<T> {

    /**
     * Use distinct.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param criteriaQuery   the criteria query
     */
    default void useDistinct(Root<T> root, CriteriaBuilder criteriaBuilder, AbstractQuery<?> criteriaQuery) {
        criteriaQuery.distinct(false);
    }

    /**
     * Find by identifier.
     *
     * @param id the id
     * @return the t
     */
    default T findByIdentifier(TId id) {
        return this.findById(id).orElse(null);
    }

    /**
     * This method use to get all the data which both match the T and
     * searchCondition's settings , and return the data page. change history: date
     * defect# person comments.
     *
     * @param searchCondition the search condition
     * @return the page
     */
    default SearchResult<T> findByCriteria(SearchCondition searchCondition) {
        Specification<T> specification = this.createSpecification(searchCondition);
        return this.findByCriteria(specification, searchCondition.getSortName(), searchCondition.getSortDirection(),
                searchCondition.getPage(), searchCondition.getSize());
    }

    /**
     * Creates the specification.
     *
     * @param searchCondition the search condition
     * @return the specification
     */
    default Specification<T> createSpecification(SearchCondition searchCondition) {
        Specification<T> specification = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = BaseRepository.this.initSearchCondition(root, criteriaBuilder,
                        criteriaQuery, searchCondition);
                BaseRepository.this.useDistinct(root, criteriaBuilder, criteriaQuery);
                return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));
            }
        };
        return specification;
    }

    /**
     * Gets the search specification.
     *
     * @param specification the specification
     * @param sortName      the sort name
     * @param sortDirection the sort direction
     * @param page          the page
     * @param pageSize      the page size
     * @return the page
     */
    default SearchResult<T> findByCriteria(Specification<T> specification, String sortName, Boolean sortDirection,
                                           Integer page, Integer pageSize) {
        Sort sort;

        if (null == sortName) {
            sort = new Sort(Sort.Direction.ASC);
        } else {
            Sort.Direction direction = Optional.ofNullable(sortDirection)
                    .map(sortDirectionValue -> sortDirectionValue ? Sort.Direction.DESC : Sort.Direction.ASC)
                    .orElse(Sort.Direction.ASC);
            sort = new Sort(direction, sortName);
        }

        final Sort sortRef = sort;

        Page<T> pageResult = Optional.ofNullable(pageSize).map((Integer size) -> {
            Integer pageIndex = Optional.ofNullable(page).map(p -> p - 1 <= 0 ? 0 : p - 1).orElse(0);
            return this.findAll(specification, PageRequest.of(pageIndex, size, sortRef));
        }).orElseGet(() -> new PageImpl<>(this.findAll(specification, sort)));

        SearchResult<T> searchResult = new SearchResult<>();
        final Long totalItems = this.count(specification);
        searchResult.setTotalItems(totalItems);
        searchResult.setPage(pageResult);

        return searchResult;
    }

    /**
     * Initialize the search condition.
     *
     * @param root            the root
     * @param criteriaBuilder the criteria builder
     * @param criteriaQuery   the criteria query
     * @param searchCondition the search condition
     * @return the list
     */
    default List<Predicate> initSearchCondition(Root<T> root, CriteriaBuilder criteriaBuilder,
                                                CriteriaQuery<?> criteriaQuery, SearchCondition searchCondition) {
        return PredicateBuilder.build(root, criteriaBuilder, searchCondition);
    }
}
