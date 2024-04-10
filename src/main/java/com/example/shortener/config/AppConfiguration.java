package com.example.shortener.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class AppConfiguration {

    /**
     * The LocaleResolver interface deals with locale resolution required when localizing web applications to specific locales.
     * CookieLocaleResolver — resolves the locale and stores it in a cookie stored on the user’s machine.
     * Now, as long as browser cookies aren’t cleared by the user, once resolved the resolved locale data
     * will last even between sessions.
     */

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setDefaultTimeZone(TimeZone.getTimeZone("UTC"));
        return localeResolver;
    }

    /**
     * Interceptor bean will intercept each request that the application receives,
     * and check for a localeData parameter on the HTTP request.
     * If found, the interceptor uses the localeResolver to register
     * the locale it found as the current user’s locale.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("localeData");
        return localeChangeInterceptor;
    }

    /**
     * The purpose of this bean is to provide message resources for internationalization (i18n)
     * in a Spring application.
     * MessageSource Class manages and access message resources for internationalization purposes.
     * It's an essential part of building applications that support multiple languages and locales.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:lang/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
