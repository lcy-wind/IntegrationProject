package com.teamwork.integrationproject.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.integrationproject.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentMapper extends BaseMapper<Student> {

    void addStudentList(List<Student> studentList);

}
