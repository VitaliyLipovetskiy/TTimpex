package com.lvv.ttimpex2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TTimpex2Application {

    public static void main(String[] args) {
        SpringApplication.run(TTimpex2Application.class, args);

//        ConfigurableApplicationContext ctx = SpringApplication.run(TTimpex2Application.class, args);
//        TimestampRepositoryImpl repository = ctx.getBean(TimestampRepositoryImpl.class);

//        System.out.println(repository.handlingParadox("D3105_22"));
    }

}
