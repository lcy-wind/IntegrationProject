package com.teamwork.integrationproject.userAggregate.entiy;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class User
{
    @TableId(value = "u_id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "u_username")
    private String username;
    @TableField(value = "u_sex")
    private String sex;
    @TableField(value = "u_age")
    private Integer age;
}
