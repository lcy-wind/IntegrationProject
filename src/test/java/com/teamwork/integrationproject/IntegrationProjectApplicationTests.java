package com.teamwork.integrationproject;

import com.teamwork.integrationproject.common.props.ApiProperties;
//import com.teamwork.integrationproject.common.rocketmq.AccountStreamService;
import com.teamwork.integrationproject.dto.StudentDto;
import com.teamwork.integrationproject.mapper.TUserMapper;
import com.teamwork.integrationproject.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
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
    private StudentServiceImpl studentService;

    @Test
    void TestStudent()
    {
//        List<StudentDto> studentDtos = studentService.selectStudentListPage();
    }

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
