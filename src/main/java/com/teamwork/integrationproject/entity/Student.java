package com.teamwork.integrationproject.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_student")
public class Student
{
    @TableField(value = "s_id")
    private Integer id;
    @TableField(value = "s_teacher")
    private String teacher;

}
