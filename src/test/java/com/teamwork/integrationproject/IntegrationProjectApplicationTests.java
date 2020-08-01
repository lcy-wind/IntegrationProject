package com.teamwork.integrationproject;

import com.teamwork.integrationproject.userAggregate.UserService;
import com.teamwork.integrationproject.userAggregate.entiy.User;
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

    @Autowired
    private UserService userService;

    @Test
    public void testDataSource() throws SQLException
    {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void kk()
    {
    }

    @Test
    void test()
    {
        User user = userService.selectUserId(1);
        System.out.println(user.toString());
    }

}
