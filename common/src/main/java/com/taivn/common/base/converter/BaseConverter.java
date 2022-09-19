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
package com.taivn.common.base.converter;

import org.modelmapper.ModelMapper;

/**
 * The Class BaseConverter.
 *
 * @param <TEntity> the generic type
 * @param <TDto>    the generic type
 */
public abstract class BaseConverter<TEntity, TDto> {

    /** The model mapper. */
    protected ModelMapper modelMapper;

    /**
     * Instantiates a new base converter.
     */
    protected BaseConverter() {
        this.modelMapper = new ModelMapper();
    }

    /**
     * To dto.
     *
     * @param entity the entity
     * @return the t dto
     */
    public abstract TDto toDto(TEntity entity);

    /**
     * To entity.
     *
     * @param dto the dto
     * @return the t entity
     */
    public abstract TEntity toEntity(TDto dto);

    /**
     * To entity.
     *
     * @param dto    the dto
     * @param entity the entity
     */
    public abstract void toEntity(TDto dto, TEntity entity);
}
