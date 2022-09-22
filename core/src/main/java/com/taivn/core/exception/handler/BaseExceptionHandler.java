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
package com.taivn.core.exception.handler;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.taivn.common.template.ResponseTemplate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Instantiates a new base exception handler.
 */
@NoArgsConstructor

/** The Constant log. */
@Slf4j
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle internal error.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    ResponseEntity<Object> handleInternalError(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        HttpStatus status = null;
        final String message = new StringBuilder(ex.getClass().getName()).append(" : ").append(ex.getMessage())
                .toString();

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            status = HttpStatus.NOT_ACCEPTABLE;
        } else if (ex instanceof MissingServletRequestParameterException || ex instanceof ServletRequestBindingException
                || ex instanceof TypeMismatchException || ex instanceof HttpMessageNotReadableException
                || ex instanceof MethodArgumentNotValidException || ex instanceof MissingServletRequestPartException
                || ex instanceof BindException || ex instanceof EmptyResultDataAccessException
                || ex instanceof EntityNotFoundException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof AsyncRequestTimeoutException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return handleExceptionInternal(ex, new ResponseTemplate(status.value(), message), new HttpHeaders(), status,
                request);
    }
}
