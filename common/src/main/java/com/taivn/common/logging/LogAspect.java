/**

 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 */
package com.taivn.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

/**
 * The Class LogAspect.
 */
@Aspect
@Slf4j
public class LogAspect {

    /**
     * Inject into a method which is marked with InjectLog annotation.
     */
    @Pointcut("@annotation(com.taivn.common.logging.InjectLog)")
    public void injectIntoMarked() {
        // Handle annotation from log
    }

    /**
     * Log before.
     *
     * @param joinPoint the join point
     */
    @Before("injectIntoMarked()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        InjectLog config = signature.getMethod().getAnnotation(InjectLog.class);

        if (config.logEnter() && log.isDebugEnabled()) {
            log.debug("{} <- Enter", joinPoint.getSignature().getName());
        }
        if (config.logParams() && joinPoint.getArgs().length > 0) {
            log.debug("{} - Params:  {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
    }

    /**
     * Log after.
     *
     * @param joinPoint the join point
     */
    @AfterReturning(pointcut = "injectIntoMarked()")
    public void logAfter(JoinPoint joinPoint) {
        if (!log.isDebugEnabled()) {
            return;
        }
        if (((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(InjectLog.class).logLeave()) {
            log.debug("{} -> Leave", joinPoint.getSignature().getName());
        }
    }
}
