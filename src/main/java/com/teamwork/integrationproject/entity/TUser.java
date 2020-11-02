package com.teamwork.integrationproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author haoChaoJie
 * @since 2020-08-05
 */
@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "u_id")
    private Integer uId;
    @TableField(value = "u_username")
    private String uUserName;
    @TableField(value = "u_sex")
    private String uSex;
    @TableField(value = "u_age")
    private Integer uAge;


}
