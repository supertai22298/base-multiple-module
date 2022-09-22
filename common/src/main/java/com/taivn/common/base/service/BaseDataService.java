/**

 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.common.base.service;

import com.taivn.common.base.converter.BaseConverter;
import com.taivn.common.base.dto.BaseDTO;
import com.taivn.common.base.dto.BaseEntity;
import com.taivn.common.base.dto.SearchResultDTO;
import com.taivn.common.base.repository.BaseRepository;
import com.taivn.common.builder.bean.SearchCondition;

import java.util.List;

/**
 * The Interface BaseDataService.
 *
 * @param <TId>     the generic type
 * @param <TEntity> the generic type
 * @param <TDto>    the generic type
 * @param <TRepo>   the generic type
 * @param <TCvt>    the generic type
 */
public interface BaseDataService<TId, TEntity extends BaseEntity<TId>, TDto extends BaseDTO<TId>, TRepo extends BaseRepository<TEntity, TId>, TCvt extends BaseConverter<TEntity, TDto>> {

    /**
     * Gets the entity name.
     *
     * @return the entity name
     */
    String getEntityName();

    /**
     * Find all.
     *
     * @return the list
     */
    List<TDto> findAll();

    /**
     * Find by criteria.
     *
     * @param searchCondition the search condition
     * @return the list
     */
    SearchResultDTO<TDto> findByCriteria(SearchCondition searchCondition);

    /**
     * Find by id.
     *
     * @param id the id
     * @param id the encrypted key
     * @return the t entity
     */
    TDto findById(TId id);

    /**
     * Insert.
     *
     * @param dto the dto
     * @return the t dto
     */
    TDto insert(TDto dto);

    /**
     * Insert DB.
     *
     * @param dto the dto
     */
    void insertDB(TDto dto);

    /**
     * Insert batch.
     *
     * @param dtoList the dto list
     * @return the list
     */
    List<TDto> insertBatch(List<TDto> dtoList);

    /**
     * Update.
     *
     * @param dto          the dto
     * @return the t dto
     */
    TDto update(TId id, TDto dto);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(TId id);

    /**
     * Delete Many.
     *
     * @param listDTO the list DTO
     */
    void deleteList(List<TDto> listDTO);

    /**
     * Update List.
     *
     * @param listDTO the list DTO
     */
    void updateList(List<TDto> listDTO);
}
