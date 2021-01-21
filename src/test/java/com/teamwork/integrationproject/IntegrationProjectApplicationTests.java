package com.teamwork.integrationproject;

import com.teamwork.integrationproject.common.props.ApiProperties;
//import com.teamwork.integrationproject.common.rocketmq.AccountStreamService;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.mapper.StudentMapper;
import com.teamwork.integrationproject.mapper.TUserMapper;
import com.teamwork.integrationproject.utils.log.LogHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

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
    public void test() throws InterruptedException {
//1、 创建CountDownLatch 对象， 设定需要计数的子线程数目
        final CountDownLatch latch=new CountDownLatch(3);
        System.out.println("主线程开始执行....");
        for (int i = 0; i < 3; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"  开始执行存储过程..");
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getName()+"  存储过程执行完毕...");
                        //2、子线程执行完毕，计数减1
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
        System.out.println("等待子线程执行完毕...");
        //3、 当前线程挂起等待
        latch.await();
        System.out.println("主线程执行完毕....");

    }
}
