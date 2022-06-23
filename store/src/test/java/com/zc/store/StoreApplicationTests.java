package com.zc.store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    DataSource dataSource;

    @DisplayName("数据库连接")
    @Test
    void contextLoads() throws SQLException {

        System.out.println(dataSource.getClass());
    }

}
