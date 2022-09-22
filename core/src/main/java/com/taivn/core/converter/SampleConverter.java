/**
 * 
 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Feb 28, 2022     ********           Taivn            Initialize
 */
package com.taivn.core.converter;

import com.taivn.entity.table.SampleTbl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.taivn.common.base.converter.BaseConverter;
import com.taivn.common.logging.InjectLog;
import com.taivn.dto.SampleDTO;

import lombok.NoArgsConstructor;

/**
 * The class ManufacturingSimulatorConverter
 *
 * @author Taivn
 */
@NoArgsConstructor
@Service
@ComponentScan
public class SampleConverter
        extends BaseConverter<SampleTbl, SampleDTO> {

    /**
     * To entity.
     *
     * @param dto the dto
     * @return the user role
     */
    @Override
    public SampleTbl toEntity(SampleDTO dto) {
        if (dto == null) {
            return null;
        }

        SampleTbl entity = new SampleTbl();
        entity.setId(dto.getId());
        entity.setSampleId(dto.getSampleId());

        return entity;
    }

    /**
     * To entity.
     *
     * @param dto    the dto
     * @param entity the entity
     */
    @Override
    public void toEntity(SampleDTO dto, SampleTbl entity) {
        entity.setId(dto.getId());
        entity.setSampleId(dto.getSampleId());
        entity.setSampleName(dto.getSampleName());
    }

    /**
     * To dto.
     *
     * @param entity the entity
     * @return the template DTO
     */
    @Override
    @InjectLog(logParams = false)
    public SampleDTO toDto(SampleTbl entity) {
        if (entity == null) {
            return null;
        }

        SampleDTO dto = new SampleDTO();
        dto.setId(entity.getId());
        dto.setSampleId(entity.getSampleId());
        dto.setSampleName(entity.getSampleName());

        return dto;
    }

    /**
     * To dto.
     *
     * @param entity              the entity
     * @param ignoreSensitiveData the ignore sensitive data
     * @return the manufacturing DTO
     */
    public SampleDTO toDto(SampleTbl entity, boolean ignoreSensitiveData) {
        if (entity == null) {
            return null;
        }

        SampleDTO dto = new SampleDTO();
        dto.setId(entity.getId());
        dto.setSampleId(entity.getSampleId());
        dto.setSampleName(entity.getSampleName());
        if (ignoreSensitiveData) {
            // TODO
        }
        return dto;
    }
}
