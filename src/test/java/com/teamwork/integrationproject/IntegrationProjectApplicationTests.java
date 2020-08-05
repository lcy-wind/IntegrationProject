package com.teamwork.integrationproject;

import com.teamwork.integrationproject.common.props.ApiProperties;
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

//    @Autowired
//    private ITUserService itUserService;

    @Autowired
    private ApiProperties properties;

    @Test
    public void testDataSource() throws SQLException
    {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void kk()
    {
        System.out.println(properties.getName());
    }

    @Test
    void test()
    {
//        User user = itUserService.(1);
//        System.out.println(user.toString());
    }

}
