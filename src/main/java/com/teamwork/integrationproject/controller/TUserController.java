package com.teamwork.integrationproject.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haoChaoJie
 * @since 2020-08-05
 */
@RestController
@RequestMapping("user")
public class TUserController {

    @GetMapping("test")
    public String test(){
        System.out.println("哈哈哈哈");
        return "hahah";
    }



}
