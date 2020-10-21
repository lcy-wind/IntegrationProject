package com.teamwork.integrationproject.controller;

import com.teamwork.integrationproject.dto.GenericTypeResponse;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2020/10/20 17:00
 */
@RestController
@RequestMapping("/v1")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    //页面数据查询
    @PostMapping("/selectStudentList")
    public GenericTypeResponse selectStudentList() {
        List<Student> students = studentService.selectStudentList();
        return new GenericTypeResponse(students);
    }

}