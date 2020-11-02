package com.teamwork.integrationproject.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_student")
public class Student
{
    @TableField(value = "s_id")
    private Integer sId;
    @TableField(value = "s_name")
    private String name;
    @TableField(value = "s_age")
    private Integer age;
    @TableField(value = "s_grade")
    private String grade;


}
