package com.teamwork.integrationproject.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.integrationproject.entity.Student;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentMapper extends BaseMapper<Student> {

    Student selectStudent(Student student);
}
