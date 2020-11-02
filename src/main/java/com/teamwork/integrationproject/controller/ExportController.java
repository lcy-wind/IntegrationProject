package com.teamwork.integrationproject.controller;

import com.teamwork.integrationproject.common.export.ExportTemplate;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Student> students = studentService.selectStudentList();
        List<Map<String, Object>> dataList = new ArrayList<>();
        students.forEach(student -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",student.getName());
            map.put("grade",student.getGrade());
            map.put("age",student.getAge());
            dataList.add(map);
        });
        ExportTemplate.exportStudent(response,"学生表导出",dataList);
    }

}