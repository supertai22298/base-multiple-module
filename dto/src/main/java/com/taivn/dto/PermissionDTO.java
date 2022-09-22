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
 * The Class PermissionDTO.
 *
 * @author Taivn
 */

@Getter
@Setter
public class PermissionDTO extends BaseDTO<Integer> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 318831807718728344L;

    /** The position id. */
    private int id;

    /** The position id. */
    private String code;

    /** The name. */
    private String displayText;

    /** The name. */
    private String description;

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @JsonIgnore
    public Integer getIdentifier() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @JsonIgnore
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
