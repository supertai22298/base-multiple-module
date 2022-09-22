/**
 * 
 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 3, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class InvalidRequestException.
 *
 * @author Taivn
 */

/**
 * Gets the checks if is handled.
 *
 * @return the checks if is handled
 */
@Getter

/**
 * Sets the checks if is handled.
 *
 * @param isHandled the new checks if is handled
 */
@Setter
public class InvalidRequestException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7252949981915663662L;

    /** The error code. */
    private final String errorCode;

    /** The is handled. */
    private final Boolean isHandled;

    /**
     * Instantiates a new base exception.
     */
    public InvalidRequestException() {
        super();
        this.errorCode = null;
        this.isHandled = false;
    }

    /**
     * Instantiates a new base exception.
     *
     * @param errorCode the error code
     * @param errorText the error text
     */
    public InvalidRequestException(final String errorCode, final String errorText) {
        super(errorText);
        this.errorCode = errorCode;
        this.isHandled = false;
    }

    /**
     * Instantiates a new base exception.
     *
     * @param errorCode the error code
     * @param errorText the error text
     * @param cause     the cause
     */
    public InvalidRequestException(final String errorCode, final String errorText, final Throwable cause) {
        super(errorText, cause);
        this.errorCode = errorCode;
        this.isHandled = false;
    }
}
