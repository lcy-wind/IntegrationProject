package com.teamwork.integrationproject.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.teamwork.integrationproject.entity.Student;

import java.util.List;


public interface StudentService extends IService<Student> {

    List<Student> selectStudentList();

}
