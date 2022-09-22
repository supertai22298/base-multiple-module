/**
 * 
 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Feb 27, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class ActionTracingInterceptor.
 */
@Component

/** The Constant log. */
@Slf4j
public class ActionTracingInterceptor implements HandlerInterceptor {

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("Action=[{}], Method=[{}] <- ENTER", request.getRequestURI(), request.getMethod());
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        log.info("Action=[{}], Method=[{}]...DONE", request.getRequestURI(), request.getMethod());
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) throws Exception {
        log.info("Action=[{}], Method=[{}] -> LEAVE", request.getRequestURI(), request.getMethod());
    }
}
