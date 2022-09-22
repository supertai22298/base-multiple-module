/**

 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Feb 17, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class HashException.
 *
 * @author taivn
 */
/**
 * Gets the error code.
 *
 * @return the error code
 */

/**
 * Gets the error code.
 *
 * @return the error code
 */
@Getter

/**
 * Sets the checks if is handled.
 *
 * @param isHandled the new checks if is handled
 */

/**
 * Sets the error code.
 *
 * @param errorCode the new error code
 */
@Setter
public class HashException extends RuntimeException {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The error code. */
    private final String errorCode;

    /**
     * Constructor.
     *
     * @param errorCode the error code
     * @param message   the message
     */
    public HashException(final String errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * The Constructor.
     *
     * @param errorCode the error code
     * @param message   the message
     * @param cause     the cause
     */
    public HashException(final String errorCode, final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
