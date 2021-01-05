package com.teamwork.integrationproject;

import com.alibaba.fastjson.JSONObject;
import com.teamwork.integrationproject.common.props.ApiProperties;
//import com.teamwork.integrationproject.common.rocketmq.AccountStreamService;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.mapper.StudentMapper;
import com.teamwork.integrationproject.mapper.TUserMapper;
import com.teamwork.integrationproject.utils.log.LogHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public void kk() throws ParseException {
        //百分比转换
        NumberFormat format = NumberFormat.getPercentInstance();
//        小数最大保留2位
//        format.setMaximumFractionDigits(2);
        String str = format.format(0.036);
        System.out.println(str);
    }

    @Autowired
    private StudentMapper studentMapper;
    @Test
    void TestStudent()
    {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setAge(1);
        student.setName("1");
        student.setGrade("1");
        Student student1 = new Student();
        student1.setAge(2);
        student1.setName("2");
        student1.setGrade("2");
        Student student2 = new Student();
        student2.setAge(3);
        student2.setName("3");
        student2.setGrade("3");
//        students.add(student);
//        students.add(student1);
//        students.add(student2);

//        studentMapper.addStudentList(students);
//        System.out.println(JSONObject.toJSONString(student));

        LogHelper.info(this,"kkkkkkk");
    }

//    @Autowired
//    private AccountStreamService streamService;
//    @Test
//    public void testRocketMQ(){
//        boolean send = streamService.send("hello -rocketMQ-->>");
//    }

    @Test
    public void test()
    {
        System.out.println(System.currentTimeMillis());

    }

}
