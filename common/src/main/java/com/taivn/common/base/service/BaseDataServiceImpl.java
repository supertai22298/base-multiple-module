/**

 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 */
package com.taivn.common.base.service;

import com.taivn.common.base.converter.BaseConverter;
import com.taivn.common.base.dto.BaseDTO;
import com.taivn.common.base.dto.BaseEntity;
import com.taivn.common.base.dto.SearchResultDTO;
import com.taivn.common.base.repository.BaseRepository;
import com.taivn.common.builder.bean.Condition;
import com.taivn.common.builder.bean.SearchCondition;
import com.taivn.common.builder.bean.SearchResult;
import com.taivn.common.logging.InjectLog;
import com.taivn.common.util.AssertUtil;
import com.taivn.common.util.CollectionUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Constant log.
 */
@Slf4j

/**
 * Instantiates a new base data service impl.
 */
@NoArgsConstructor
public abstract class BaseDataServiceImpl<TId, TEntity extends BaseEntity<TId>, TDto extends BaseDTO<TId>, TRepo extends BaseRepository<TEntity, TId>, TCvt extends BaseConverter<TEntity, TDto>>
        implements BaseDataService<TId, TEntity, TDto, TRepo, TCvt> {

    /**
     * The repository.
     */
    @Autowired
    protected TRepo repository;

    /**
     * The converter.
     */
    @Autowired
    protected TCvt converter;

    /**
     * Find all.
     *
     * @return the list
     */
    public List<TDto> findAll() {
        List<TDto> dtoList = null;
        List<TEntity> entityList = this.repository.findAll();

        if (!CollectionUtil.isNullOrEmpty(entityList)) {
            dtoList = new ArrayList<>(entityList.size());

            log.debug("findAll - num_entities=[{}]", entityList.size());

            for (TEntity entity : entityList) {
                dtoList.add(this.converter.toDto(entity));
            }

            return dtoList;
        }

        return dtoList;
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public SearchResultDTO<TDto> findByCriteria(SearchCondition searchCondition) {
        SearchResultDTO<TDto> searchResult = new SearchResultDTO<>();
        SearchResult<TEntity> entityPage = this.repository.findByCriteria(searchCondition);
        List<TEntity> entityList = entityPage.getPage().getContent();
        log.debug("findByCriteria - num_entities=[{}]", entityList.size());

        if (!CollectionUtil.isNullOrEmpty(entityList)) {
            searchResult.setTotalItems(entityPage.getTotalItems());
            List<TDto> dtoList = new ArrayList<>(entityList.size());

            for (TEntity entity : entityList) {
                dtoList.add(this.converter.toDto(entity));
            }

            this.updateRelations(searchCondition, entityList, dtoList);

            searchResult.setItems(dtoList);
        }

        return searchResult;
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public TDto findById(TId id) {
        return this.converter.toDto(this.repository.findByIdentifier(id));
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public TDto insert(TDto dto) {
        TEntity entity = this.converter.toEntity(dto);
        return this.converter.toDto(this.repository.save(entity));
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public void insertDB(TDto dto) {
        TEntity entity = this.converter.toEntity(dto);
        this.repository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public List<TDto> insertBatch(List<TDto> dtoList) {
        List<TEntity> entityList = new ArrayList<>();

        for (TDto dto : dtoList) {
            TEntity entity = this.converter.toEntity(dto);
            entityList.add(entity);
        }

        List<TDto> savedDtoList = new ArrayList<>();
        List<TEntity> savedEntityList = this.repository.saveAll(entityList);

        for (TEntity entity : savedEntityList) {
            TDto dto = this.converter.toDto(entity);
            savedDtoList.add(dto);
        }

        return savedDtoList;
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public TDto update(TId id, TDto dto) {
        TEntity entity = this.repository.findByIdentifier(id);
        AssertUtil.notNull(entity, dto.getIdentifier());

        this.converter.toEntity(dto, entity);
        return this.converter.toDto(this.repository.save(entity));
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public void delete(TId id) {
        this.repository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public void deleteList(List<TDto> listDTO) {
        if (!CollectionUtil.isNullOrEmpty(listDTO)) {
            for (int i = 0; i < listDTO.size(); i++) {
                this.repository.deleteById(listDTO.get(i).getIdentifier());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @InjectLog(logParams = false)
    public void updateList(List<TDto> listDTO) {
        if (!CollectionUtil.isNullOrEmpty(listDTO)) {
            for (int i = 0; i < listDTO.size(); i++) {
                this.repository.save(this.converter.toEntity(listDTO.get(i)));
            }
        }
    }

    /**
     * Update relations.
     *
     * @param searchCondition the search condition
     * @param entityList      the entity list
     * @param dtoList         the dto list
     */
    protected void updateRelations(SearchCondition searchCondition, List<TEntity> entityList, List<TDto> dtoList) {
        // Implement by concrete classes
    }

    /**
     * Find criteria.
     *
     * @param searchCondition the search condition
     * @param key             the key
     * @return the pair
     */
    protected Pair<Boolean, Object> findCriteria(SearchCondition searchCondition, String key) {
        Pair<Boolean, Object> criteria = Pair.of(false, null);
        for (Condition condition : searchCondition.getConditions()) {
            if (condition.getKey().equalsIgnoreCase(key)) {
                criteria = Pair.of(true, condition.getValue());
                break;
            }
        }
        return criteria;
    }

    /**
     * Builds the reference map.
     *
     * @param <TRef>          the generic type
     * @param <TRefRepo>      the generic type
     * @param searchCondition the search condition
     * @param refName         the ref name
     * @param refRepository   the repository
     * @return the map
     */
    protected <TRef, TRefRepo extends BaseRepository<TRef, Integer>> Map<Integer, TRef> buildRefMap(
            SearchCondition searchCondition, String refName, TRefRepo refRepository) {
        Map<Integer, TRef> refMap = new HashMap<>();
        Pair<Boolean, Object> refCriteria = this.findCriteria(searchCondition, refName);

        if (refCriteria.getLeft()) { // Found
            String searchValue = refCriteria.getRight().toString();
            List<String> searchValueList = this.createValueList(searchValue);

            for (String value : searchValueList) {
                Integer refId = Integer.parseInt(value);
                if (!refMap.containsKey(refId)) {
                    refMap.put(refId, refRepository.findByIdentifier(refId));
                }
            }
        }

        return refMap;
    }

    /**
     * Creates the value list.
     *
     * @param value the value
     * @return the list
     */
    protected List<String> createValueList(String value) {
        List<String> valueList = null;

        if (value.contains(",")) {
            valueList = Arrays.stream(value.split(",")).map(String::trim).collect(Collectors.toList());
        } else {
            valueList = new ArrayList<>();
            valueList.add(value);
        }

        return valueList;
    }
}
