package com.lvv.ttimpex2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TTimpex2Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TTimpex2Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TTimpex2Application.class, args);

//        LocalDate from = LocalDate.of(2020, 1, 1);
//        LocalDate to = LocalDate.of(2020, 1, 9);
//        from.datesUntil(to)
//                .forEach(System.out::println);

//        ConfigurableApplicationContext ctx = SpringApplication.run(TTimpex2Application.class, args);
//        TimestampRepositoryImpl repository = ctx.getBean(TimestampRepositoryImpl.class);

//        System.out.println(repository.handlingParadox("D3105_22"));
    }
}
