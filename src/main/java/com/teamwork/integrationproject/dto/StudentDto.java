package com.teamwork.integrationproject.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentDto
{

    //名称
    private String name;
    //年龄
    private Integer age;
    //班级
    private String grade;
    //id
    private Integer sId;
}
