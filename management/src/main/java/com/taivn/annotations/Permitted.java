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
package com.taivn.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface Permitted.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permitted {
    /**
     * Feature groups.
     *
     * @return the int[]
     */
    int[] features() default {};

    /**
     * User types.
     *
     * @return the int[]
     */
    int[] userTypes() default {};

    /**
     * Checks if is public.
     *
     * @return true, if is public
     */
    boolean isPublic() default false;

    /**
     * Checks if is private.
     *
     * @return true, if is private
     */
    boolean isPrivate() default false;
}
