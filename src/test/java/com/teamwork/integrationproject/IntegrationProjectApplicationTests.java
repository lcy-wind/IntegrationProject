package com.teamwork.integrationproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class IntegrationProjectApplicationTests
{

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException
    {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void contextLoads()
    {

    }

}
