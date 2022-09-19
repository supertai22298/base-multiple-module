/**
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 */
package com.taivn.common.exception;

/**
 * The Class NotFoundException.
 */
public class NotFoundException extends UserDefinedException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -7156704242765099167L;

    /**
     * Constructor.
     *
     * @param errorCode the error code
     * @param errorText the error text
     */
    public NotFoundException(final String errorCode, final String errorText) {
        super(errorCode, errorText);
    }

    /**
     * The Constructor.
     *
     * @param errorCode the error code
     * @param errorText the error text
     * @param cause     the cause
     */
    public NotFoundException(final String errorCode, final String errorText, final Throwable cause) {
        super(errorCode, errorText, cause);
    }
}
