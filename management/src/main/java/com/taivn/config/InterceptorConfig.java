/**
 * 
 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Feb 27, 2022     ********           Taivn            Initialize
 */
package com.taivn.config;

import com.taivn.common.service.I18nMessageService;
import com.taivn.core.service.PermissionService;
import com.taivn.interceptor.ActionTracingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * The Class InterceptorConfig.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /** The action tracing interceptor. */
    @Autowired
    private ActionTracingInterceptor actionTracingInterceptor;

    /**
     * {@inheritDoc}
     *
     * @see WebMvcConfigurer#addInterceptors(
     *InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(actionTracingInterceptor);
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * localeChangeInterceptor
     *
     * @return lci
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * localeResolver
     *
     * @return slr
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     * messageSource
     *
     * @return messageSource
     */
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * initialization i18nMessageService
     *
     * @return new i18nMessageService
     */
    @Bean("i18nMessageService")
    public I18nMessageService i18nMessageService() {
        return new I18nMessageService();
    }

    /**
     * initialization i18nMessageService
     *
     * @return new i18nMessageService
     */
    @Bean("permissionService")
    public PermissionService permissionService() {
        return new PermissionService();
    }

    /**
     * Cors filter.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);

        return bean;
    }
}
