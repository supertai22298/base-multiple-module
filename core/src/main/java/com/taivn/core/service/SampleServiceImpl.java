/**
 * 
 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Feb 27, 2022     ********           Taivn            Initialize
 */
package com.taivn.core.service;


import com.taivn.dto.CreateSampleDTO;
import com.taivn.entity.table.SampleTbl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.taivn.common.base.service.BaseDataServiceImpl;
import com.taivn.core.converter.SampleConverter;
import com.taivn.dto.SampleDTO;
import com.taivn.repository.SampleRepository;

import lombok.NoArgsConstructor;

/**
 * The Class SampleDataServiceImpl.
 *
 * @author Taivn
 */

/**
 * Instantiates a new user data service impl.
 */
@NoArgsConstructor
@Service
@ComponentScan
public class SampleServiceImpl extends
        BaseDataServiceImpl<Integer, SampleTbl, SampleDTO, SampleRepository, SampleConverter>
        implements SampleService {

    /**
     * The repository of sample layout
     */
    @Autowired
    private SampleRepository sampleRepository;


    /**
     * The converter of summary simulation
     */
    @Autowired
    private SampleConverter sampleConverter;

//    @PersistenceContext
//    EntityManager entityManager;

    /**
     * Gets the entity name.
     *
     * @return the entity name
     */
    public String getEntityName() {
        return SampleTbl.class.getSimpleName();
    }

    /**
     * Create record on table
     *
     * @return the entity
     */
    @Override
    public SampleDTO createSampleRecord(CreateSampleDTO createSampleDTO) {
        SampleDTO retDTO = new SampleDTO();
        SampleTbl newRecord = new SampleTbl();

        String sampleId = createSampleDTO.getSampleId();
//        if (this.sampleRepository.findFirstBySampleId(sampleId) == null) {
            newRecord.setSampleId(sampleId);
            newRecord.setSampleName(createSampleDTO.getSampleName());
            SampleTbl entity = this.sampleRepository.save(newRecord);
            retDTO = this.sampleConverter.toDto(entity);
//        }
        return retDTO;
    }

    @Override
    public SampleDTO getSampleById(String sampleId) {
        SampleDTO retDTO = new SampleDTO();
        SampleTbl exitsRecord = this.sampleRepository.findFirstBySampleId(sampleId);
        if (exitsRecord != null) {
            retDTO = this.sampleConverter.toDto(exitsRecord);
        }

        return retDTO;
    }
}
