package com.teamwork.integrationproject;

import com.alibaba.fastjson.JSONObject;
import com.teamwork.integrationproject.common.props.ApiProperties;
import com.teamwork.integrationproject.common.rocketmq.AccountStreamService;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.mapper.StudentMapper;
import com.teamwork.integrationproject.mapper.TUserMapper;
import com.teamwork.integrationproject.utils.log.LogHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class IntegrationProjectApplicationTests
{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TUserMapper userMapper;

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

    @Autowired
    private StudentMapper studentMapper;
    @Test
    void TestStudent()
    {
        Student student1 = new Student();
//        student1.setSTeacher("王大咪");
        Student student = studentMapper.selectStudent(student1);
        System.out.println(JSONObject.toJSONString(student));

        LogHelper.info(this,"kkkkkkk");
    }

    @Autowired
    private AccountStreamService streamService;
    @Test
    public void testRocketMQ(){
        boolean send = streamService.send("hello -rocketMQ-->>");
    }

}
