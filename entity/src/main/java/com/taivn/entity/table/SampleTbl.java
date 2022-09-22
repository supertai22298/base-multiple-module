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
package com.taivn.entity.table;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.taivn.common.base.dto.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

/**
 * The Class Sample Entity.
 *
 * @author Taivn
 */
@Entity
@Table(name = "sample_table", uniqueConstraints = @UniqueConstraint(columnNames = {"sample_id"}))
@Getter
@Setter
@NoArgsConstructor
public class SampleTbl extends BaseEntity<Integer> {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 8107092578377879894L;

    /**
     * The sample id.
     */
    @Column(name = "sample_id", length = 10)
    private String sampleId;

    /**
     * The createdDate.
     */
    @Column(name = "sample_name")
    private String sampleName;

    /**
     * The createdDate.
     */
    @CreatedDate
    @Column(name = "created_date")
    private Timestamp createdDate;

    /**
     * The createdBy.
     */
    @CreatedBy
    @Column(name = "created_by", length = 10)
    private Integer createdBy;

    /**
     * The updatedDate.
     */
    @UpdateTimestamp
    @Column(name = "updated_date")
    private Timestamp updatedDate;

    /**
     * The updatedBy.
     */
    @Column(name = "updated_by", length = 10)
    private Integer updatedBy;

}
