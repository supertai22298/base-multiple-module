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
package com.taivn.repository;

import com.taivn.common.base.repository.BaseRepository;
import com.taivn.entity.table.SampleTbl;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface SampleRepository extends BaseRepository<SampleTbl, Integer> {

    List<SampleTbl> findAllById(String id);

    SampleTbl findFirstBySampleId(String sampleId);
}
