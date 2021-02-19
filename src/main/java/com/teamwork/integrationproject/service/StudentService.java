package com.teamwork.integrationproject.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.teamwork.integrationproject.dto.StudentDto;
import com.teamwork.integrationproject.entity.Student;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public interface StudentService extends IService<Student> {

    List<StudentDto> selectStudentList();

    List<StudentDto> selectStudentListPage(HttpServletResponse response) throws IOException;

    void addStudentList(List<Student> studentList);
}
