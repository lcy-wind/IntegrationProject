package com.teamwork.integrationproject.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.mapper.StudentMapper;
import com.teamwork.integrationproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public void kkk()
    {
        Student student = studentMapper.selectById(1);
        System.out.println(student);
    }

    @Override
    public List<Student> selectStudentList()
    {
        return studentMapper.selectList(null);
    }
}
