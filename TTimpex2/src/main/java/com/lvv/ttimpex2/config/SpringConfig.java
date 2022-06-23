package com.lvv.ttimpex2.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Vitalii Lypovetskyi
 */
//@Configuration
public final class SpringConfig {

//    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.googlecode.paradox.Driver");
//        dataSource.setCatalog("jdbc:paradox:/Users/vitaliy/IdeaProjects/db/");
        dataSource.setUrl("jdbc:paradox:/Users/vitaliy/IdeaProjects/db/");
//        dataSource.set
        return dataSource;
    }

//    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
