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
package com.taivn.filter;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class SimpleCorsFilter.
 */
@Component

/**
 * Instantiates a new simple cors filter.
 */
@NoArgsConstructor
public class SimpleCorsFilter implements Filter {

    /**
     * {@inheritDoc}
     * 
     * @see Filter#doFilter(ServletRequest,
     *      ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Accept, X-Requested-With, remember-me, X-Custom-header, Authorization");
        response.setHeader("Access-Control-Expose-Headers",
                "accept, Authorization, content-type, x-requested-with, jwt");

        chain.doFilter(req, res);
    }

    /**
     * {@inheritDoc}
     *
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // Waiting for implementation.
    }

    /**
     * {@inheritDoc}
     *
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        // Waiting for implementation.
    }
}
