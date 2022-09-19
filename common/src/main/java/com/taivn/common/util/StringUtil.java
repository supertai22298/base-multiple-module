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
package com.taivn.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * Instantiates a new string util.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {

    /**
     * Trim URL.
     *
     * @param url the URL
     * @return the string
     */
    public static String trimUrl(String url) {
        String adjustedUrl = url;
        if (url.endsWith("/")) {
            adjustedUrl = url.substring(0, url.length() - 1);
        }
        return adjustedUrl;
    }

    public static boolean isEmptyString(String input) {
        return StringUtils.isEmpty(input) || StringUtils.isEmpty(input.trim());
    }

    public static boolean isNotEmptyString(String input) {
        return !isEmptyString(input);
    }

}
