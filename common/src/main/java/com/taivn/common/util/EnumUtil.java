/**

 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Feb 28, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.common.util;

import java.util.Arrays;

/**
 * The Class EnumUtils.
 *
 * @author Taivn
 */
public final class EnumUtil {

    /**
     * Instantiates a new enum utils.
     */
    private EnumUtil() {
        // do nothing.
    }

    /**
     * Gets the names.
     *
     * @param e the e
     * @return the names
     */
    public static String[] toArray(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
