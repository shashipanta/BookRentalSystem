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
@Profile("test")
public class H2DatabaseConfig {

    @Value("${spring.datasource.h2.url}")
    private String url;

    @Value("${spring.datasource.h2.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.h2.username}")
    private String username;

    @Value("${spring.datasource.h2.password}")
    private String password;

    @Value("${spring.jpa.h2.database-platform}")
    private String databasePlatform;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
