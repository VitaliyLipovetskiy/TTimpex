package com.lvv.ttimpex2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Configuration
//@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages/app");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML));
        converters.add(stringConverter);
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        jsonConverter.setObjectMapper(objectMapper);
        converters.add(jsonConverter);
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        return bean;
    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.googlecode.paradox.Driver");
////        dataSource.setCatalog("jdbc:paradox:/Users/vitaliy/IdeaProjects/db/");
//        dataSource.setUrl("jdbc:paradox:/Users/vitaliy/IdeaProjects/db/");
////        dataSource.set
//        return dataSource;
//    }

//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
