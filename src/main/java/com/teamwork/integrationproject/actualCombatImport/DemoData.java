package com.teamwork.integrationproject.actualCombatImport;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2021/1/5 11:32
 */
@Data
@TableName("t_student")
public class DemoData
{
//    @TableField(value = "s_id")
//    private Integer sId;
    @TableField(value = "s_name")
    private String name;
    @TableField(value = "s_age")
    private Integer age;
    @TableField(value = "s_grade")
    private String grade;
}
