package com.lvv.ttimpex2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.time.format.DateTimeFormatter;

/**
 * @author Vitalii Lypovetskyi
 */
//@Configuration
public class DateTimeConfig extends WebMvcConfigurationSupport {

    @Bean
    @Override
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());

        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
//        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ISO_LOCAL_DATE);
//        dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        dateTimeRegistrar.setTimeFormatter(DateTimeFormatter.ISO_LOCAL_TIME);
        dateTimeRegistrar.registerFormatters(conversionService);

        DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
        dateRegistrar.setFormatter(new DateFormatter("dd.MM.yyyy"));
        dateRegistrar.registerFormatters(conversionService);

        return conversionService;
    }
}