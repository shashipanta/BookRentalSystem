package com.brs.bookrentalsystem.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class MySQLDataSourceConfig {


    @Value("${spring.datasource.mysql.url}")
    private String url;

    @Value("${spring.datasource.mysql.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.mysql.username}")
    private String username;

    @Value("${spring.datasource.mysql.password}")
    private String password;

    @Value("${spring.jpa.mysql.database-platform}")
    private String databasePlatform;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
