package com.teamwork.integrationproject.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teamwork.integrationproject.actualCombatImport.DemoData;
import com.teamwork.integrationproject.dto.StudentDto;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.mapper.StudentMapper;
import com.teamwork.integrationproject.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<StudentDto> selectStudentList()
    {
        List<Student> students = studentMapper.selectList(null);
        List<StudentDto> studentDtoList= new ArrayList<>();
        //集合判断
        if (!CollectionUtils.isEmpty(students)){
            studentDtoList = students.stream().map(student -> {
                StudentDto studentDto = new StudentDto();
                //属性拷贝
                BeanUtils.copyProperties(student, studentDto);
                return studentDto;
            }).collect(Collectors.toList());
        }
        return studentDtoList;
    }

    @Override
    public void addStudentList(List<Student> studentList) {
        studentMapper.addStudentList(studentList);
    }
}
