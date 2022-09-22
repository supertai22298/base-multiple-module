/**
 * 
 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 1, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.core.exception;

/**
 * The Class TokenExpiredException.
 *
 * @author Taivn
 */
public class TokenExpiredException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7608157964961848795L;

    /**
     * Instantiates a new token expired exception.
     */
    public TokenExpiredException() {
        super("TokenExpiredException");
    }
}
