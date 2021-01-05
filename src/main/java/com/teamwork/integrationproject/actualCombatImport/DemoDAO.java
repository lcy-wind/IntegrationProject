package com.teamwork.integrationproject.actualCombatImport;

import com.alibaba.fastjson.JSONObject;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.service.impl.StudentServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2021/1/5 11:35
 */

@Repository
public class DemoDAO
{
    @Autowired
    private StudentServiceImpl studentService;

    public void save(List<DemoData> list) {
        List<Student> students = list.stream().map(demoData -> {
            Student student = new Student();
            BeanUtils.copyProperties(demoData, student);
            return student;
        }).collect(Collectors.toList());
        studentService.addStudentList(students);
        System.out.println(JSONObject.toJSONString(list));
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    }
}
