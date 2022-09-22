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

import com.taivn.common.base.service.BaseDataService;
import com.taivn.core.converter.SampleConverter;
import com.taivn.dto.CreateSampleDTO;
import com.taivn.dto.SampleDTO;
import com.taivn.entity.table.SampleTbl;
import com.taivn.repository.SampleRepository;

/**
 * @author Taivn
 */
public interface SampleService extends
        BaseDataService<Integer, SampleTbl, SampleDTO, SampleRepository, SampleConverter> {

    SampleDTO createSampleRecord(CreateSampleDTO createSampleDTO);

    SampleDTO getSampleById(String sampleId);
}
