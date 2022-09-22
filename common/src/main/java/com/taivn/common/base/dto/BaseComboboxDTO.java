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
package com.taivn.common.base.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class BaseComboboxDTO.
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
public class BaseComboboxDTO extends SerializableDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 48281672892012112L;

    /** The position id. */
    private String key;

    /** The name. */
    private String value;
}
