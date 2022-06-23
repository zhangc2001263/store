package com.zc.store.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyDataSource {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {

        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}
