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
package com.taivn.common.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface InjectLog.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectLog {

    /**
     * Log params.
     *
     * @return true, if successful
     */
    boolean logParams() default true;

    /**
     * Log enter.
     *
     * @return true, if successful
     */
    boolean logEnter() default true;

    /**
     * Log leave.
     *
     * @return true, if successful
     */
    boolean logLeave() default true;
}
