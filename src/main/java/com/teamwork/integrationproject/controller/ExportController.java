package com.teamwork.integrationproject.controller;

import com.alibaba.excel.EasyExcel;
import com.teamwork.integrationproject.actualCombatImport.TestDemo;
import com.teamwork.integrationproject.common.export.ExportTemplate;
import com.teamwork.integrationproject.common.export.TemplateDesign;
import com.teamwork.integrationproject.dto.GenericTypeResponse;
import com.teamwork.integrationproject.dto.StudentDto;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.service.StudentService;
import com.teamwork.integrationproject.utils.excel.ExcelImportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2020/10/21 11:00
 */
@RestController
@RequestMapping("v1")
public class ExportController {

    @Autowired
    private StudentService studentService;

    //针对表数据进行导出
    @PostMapping("/exportStudentList")
    public void exportStudentList(HttpServletRequest request, HttpServletResponse response) {
        List<StudentDto> studentDtoList = studentService.selectStudentList();
        List<Map<String, Object>> dataList = new ArrayList<>();
        studentDtoList.forEach(student -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",student.getName());
            map.put("grade",student.getGrade());
            map.put("age",student.getAge());
            dataList.add(map);
        });
        ExportTemplate.exportStudent(response,"学生表导出",dataList);
    }

    /**
     * 导入student
     * @param file
     */
    @PostMapping("/importStudent")
    public GenericTypeResponse importStudent(@RequestParam("file") MultipartFile file) {
        // 从Excel第一行起到最后一行结束,
        try {
            List<Student> students = new ArrayList<>();
            List<List<String>>  importData = ExcelImportUtils.readExcel(file,0,2);
            importData.forEach(data -> {
                Student student = new Student();
                student.setName(data.get(0));
                student.setAge(Integer.parseInt(data.get(1)));
                student.setGrade(data.get(2));
                students.add(student);
            });
            GenericTypeResponse<Object> objectGenericTypeResponse = new GenericTypeResponse<>();
            studentService.addStudentList(students);
            return objectGenericTypeResponse;
        } catch (IOException e) {
            throw new RuntimeException("导入异常!",e);
        }
    }


    //针对表数据进行导出
    @PostMapping("/test")
    public GenericTypeResponse test(HttpServletResponse response) throws IOException {
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName = URLEncoder.encode("吼吼哈哈", "UTF-8").replaceAll("\\+", "%20");
//        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//        List<TestDemo> testDemos = new ArrayList<>();
//        testDemos.add(TestDemo.builder().kk("我觉得我能成功？").build());
//        EasyExcel.write(response.getOutputStream(), TestDemo.class).sheet("模板")
//                .doWrite(testDemos);
        studentService.selectStudentListPage(response);
        GenericTypeResponse<Object> objectGenericTypeResponse = new GenericTypeResponse<>();
        return objectGenericTypeResponse;
    }
    //模板设计样式
    @PostMapping("/templateDesign")
    public void TemplateDesign(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("呼.....", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<TemplateDesign> templateDesigns = new ArrayList<>();
        TemplateDesign templateDesign = new TemplateDesign();
        templateDesign.setAge(1);
        templateDesign.setGrade("llll");
        templateDesign.setUsername("刺激");
        TemplateDesign templateDesign1 = new TemplateDesign();
        templateDesign1.setAge(2);
        templateDesign1.setGrade("333");
        templateDesign1.setUsername("刺激呀");
        templateDesigns.add(templateDesign);
        templateDesigns.add(templateDesign1);
        EasyExcel.write(response.getOutputStream(), TemplateDesign.class).sheet("模板")
                .doWrite(templateDesigns);
    }
}