package com.store.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;

@Configuration
public class AppDatasource {

    @Bean
    public SimpleJdbcCall simpleJdbcCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource);
    }
}
