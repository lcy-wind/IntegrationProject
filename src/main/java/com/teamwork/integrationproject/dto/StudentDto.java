package com.teamwork.integrationproject.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentDto
{

    //名称
    @ExcelProperty(value = "名称")
    private String name;
    //年龄
    @ExcelProperty(value = "年龄")
    private Integer age;
    //班级
    @ExcelProperty(value = "班级")
    private String grade;
    //id
    @ExcelProperty(value = "id")
    private Integer sId;
}
