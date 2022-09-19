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
package com.taivn.common.enums;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class ErrorDetail.
 */
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@ToString
public class ErrorDetailEnum {

    /** The code. */
    private String code;

    /** The message. */
    private String message;

    /**
     * Instantiates a new error detail.
     *
     * @param code the code
     */
    public ErrorDetailEnum(String code) {
        super();
        this.code = code;
    }

    /**
     * Instantiates a new error detail.
     *
     * @param code    the code
     * @param message the message
     */
    public ErrorDetailEnum(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
