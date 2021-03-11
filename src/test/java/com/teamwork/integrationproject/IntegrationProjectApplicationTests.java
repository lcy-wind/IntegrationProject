package com.teamwork.integrationproject;

import com.alibaba.excel.EasyExcel;
import com.teamwork.integrationproject.actualCombatImport.TestDemo;
import com.teamwork.integrationproject.common.props.ApiProperties;
import com.teamwork.integrationproject.controller.EmailController;
import com.teamwork.integrationproject.mapper.TUserMapper;
import com.teamwork.integrationproject.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
    @Autowired
    private EmailController emailController;

    @Test
    void Test()
    {
        List<TestDemo> list = new ArrayList<TestDemo>();
        for (int i = 0; i < 10; i++) {
            TestDemo data = new TestDemo();
            data.setKk("字符串" + i);
            list.add(data);
        }
        //固定文件路径写入
        String path = "C:\\Users\\admin\\Desktop\\";
        String fileName = path+ System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, TestDemo.class).sheet("模板").doWrite(list);
        //调用邮件服务发送
        emailController.sendEmail(fileName);
    }
}
