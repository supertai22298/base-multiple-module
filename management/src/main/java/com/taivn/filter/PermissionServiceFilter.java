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
package com.taivn.filter;

import com.taivn.annotations.Permitted;
import com.taivn.common.constant.Constants;
import com.taivn.common.enums.ErrorCodes;
import com.taivn.common.enums.ErrorDetailEnum;
import com.taivn.common.service.I18nMessageService;
import com.taivn.common.template.ResponseTemplate;
import com.taivn.common.util.MapperUtil;
import com.taivn.core.exception.RequestNotFoundException;
import com.taivn.core.exception.TokenExpiredException;
import com.taivn.core.exception.UnauthorizedException;
import com.taivn.dto.PermissionDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The Class PermissionServiceInterceptor.
 *
 * @author Taivn
 */

/**
 * The Constant log.
 */
@Component

/**
 * Instantiates a new jwt request filter.
 */

/**
 * Instantiates a new permission service filter.
 */
@NoArgsConstructor

/** The Constant log. */
@Slf4j
public class PermissionServiceFilter extends OncePerRequestFilter {

    /** The Authorization. */
    private static String AUTHORIZATION = "Authorization";

    /** The request mapping handler mapping. */
    private static String REQUEST_MAPPING_HANDLER_MAPPING = "requestMappingHandlerMapping";

    /** The app context. */
    @Autowired
    private org.springframework.context.ApplicationContext appContext;

    /** The i 18 n message service. */
    @Autowired
    protected I18nMessageService i18nMessageService;

    /**
     * The is ignore filter.
     */
    @Value(("${jwt.ignore-filter}"))
    private boolean isIgnoreFilter;

    /** The jwt exp. */
    @Value(("${jwt.exp}"))
    private Integer jwtExp;

    /**
     * Do filter internal.
     *
     * @param request  the request
     * @param response the response
     * @param chain    the chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        HandlerMethod handlerMethod = null;

        try {
            handlerMethod = this.getHandle(request);

            PermissionDTO tokenInfo;
            if (handlerMethod != null) {
                tokenInfo = this.validateRequest(request, handlerMethod);
                request.setAttribute(Constants.ACCESS_USER, tokenInfo);
            }

            chain.doFilter(request, response);
        } catch (Exception ex) {
            this.writeException(response, ex);
        }
    }

    /**
     * Validate request.
     *
     * @param request       the request
     * @param handlerMethod the handler method
     * @throws AccessDeniedException    the access denied exception
     * @throws RequestNotFoundException the request not found exception
     */
    private PermissionDTO validateRequest(HttpServletRequest request, HandlerMethod handlerMethod)
            throws AccessDeniedException, RequestNotFoundException {
        // user token info.
        PermissionDTO tokenInfo = null;

        // check request is public or not.
        if (!this.isPublicAccess(handlerMethod.getMethodAnnotation(Permitted.class))) {
            // request is not a public request.
            String token = request.getHeader(AUTHORIZATION);
            throw new AccessDeniedException("");
        }

        // return accessed user.
        return tokenInfo;
    }

    /**
     * Checks if is public access.
     *
     * @param handleInfo the handle info
     * @return true, if is public access
     */
    private boolean isPublicAccess(Permitted handleInfo) {
        return handleInfo != null && handleInfo.isPublic();
    }

    /**
     * Gets the handle.
     *
     * @param request the request
     * @return the handle
     */
    private HandlerMethod getHandle(HttpServletRequest request) {
        RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping) appContext
                .getBean(REQUEST_MAPPING_HANDLER_MAPPING);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

        RequestMappingInfo requestMappingInfo = handlerMethods.entrySet().stream()
                .filter(entry -> entry.getKey().getMatchingCondition(request) != null).findFirst()
                .map(Map.Entry::getKey).orElse(null);

        if (Objects.nonNull(requestMappingInfo)) {
            return handlerMethods.get(requestMappingInfo);
        }

        return null;
    }

    /**
     * Write exception. This method is used for all authentication logic. In case of
     * there're exception, used method only call this method & pass corresponding
     * parameters for write exception to client.
     *
     * @param response  the response
     * @param exception the exception
     */
    private void writeException(HttpServletResponse response, Exception exception) {

        try {
            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());

            long id = 0;
            String message = null;
            ResponseTemplate template = null;
            List<ErrorDetailEnum> errors = new ArrayList<>();

            // Catch url invalid from client
            if (exception instanceof RequestNotFoundException
                    || exception instanceof HttpRequestMethodNotSupportedException) {

                id = ErrorCodes.REQUEST_NOT_FOUND_ID;
                message = i18nMessageService.getMessage(ErrorCodes.REQUEST_NOT_FOUND);
                errors.add(new ErrorDetailEnum(ErrorCodes.REQUEST_NOT_FOUND, message));

            } else if (exception instanceof AccessDeniedException) {

                id = ErrorCodes.ACCESS_DENIED_ID;
                message = i18nMessageService.getMessage(ErrorCodes.ACCESS_DENIED);
                errors.add(new ErrorDetailEnum(ErrorCodes.ACCESS_DENIED, message));

            } else if (exception instanceof UnauthorizedException) {

                id = ErrorCodes.UNAUTHORIZED_ID;
                message = i18nMessageService.getMessage(ErrorCodes.UNAUTHORIZED);
                errors.add(new ErrorDetailEnum(ErrorCodes.UNAUTHORIZED, message));

            } else if (exception instanceof TokenExpiredException) {

                id = ErrorCodes.TOKEN_EXPIRED_ID;
                message = i18nMessageService.getMessage(ErrorCodes.TOKEN_EXPIRED);
                errors.add(new ErrorDetailEnum(ErrorCodes.TOKEN_EXPIRED, message));

            }

            if (!errors.isEmpty()) {
                template = new ResponseTemplate(id, HttpStatus.BAD_REQUEST, message);
                template.setErrors(errors);
            } else {
                template = new ResponseTemplate(HttpStatus.OK, "");
            }

            // Write detail error to response
            response.getWriter().write(MapperUtil.toJson(template));
            response.getWriter().close();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
