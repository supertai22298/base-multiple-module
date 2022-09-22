/**
 * 
 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Feb 27, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taivn.common.base.dto.BaseDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ManufacturingSimulatorDTO.
 *
 * @author Taivn
 */

/**
 * Gets the name.
 *
 * @return the name
 */
@Getter

/**
 * Sets the name.
 *
 * @param name the new name
 */
@Setter
public class SampleDTO extends BaseDTO<Integer> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8721078598210655052L;

    /** The id. */
    private int id;

    /** The employee id. */
    private String sampleId;

    /** The df timed */
    private String sampleName;

    /**
     * {@inheritDoc}
     * 
     * @see com.taivn.common.base.dto.BaseDTO#getIdentifier()
     */
    @Override
    @JsonIgnore
    public Integer getIdentifier() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.taivn.common.base.dto.BaseDTO#setIdentifier(java.lang.Object)
     */
    @Override
    @JsonIgnore
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
