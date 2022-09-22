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
package com.taivn.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Taivn
 *
 */
public final class Authorize {

    /** The authorize token prefix. */
    private static String AUTHORIZE_TOKEN_PREFIX = "Bearer: ";

    /**
     * Instantiates a new authorize utility.
     */
    private Authorize() {
        // do nothing.
    }

    /**
     * Gets the token.
     *
     * @param bearerToken the bearer token
     * @return the token
     */
    public static String getToken(String bearerToken) {
        if (StringUtils.isEmpty(bearerToken) || !bearerToken.startsWith(AUTHORIZE_TOKEN_PREFIX)) {
            return null;
        }

        return bearerToken.substring(AUTHORIZE_TOKEN_PREFIX.length()).trim();
    }
}
