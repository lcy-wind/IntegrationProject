package com.teamwork.integrationproject.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/s")
public class UserController
{
    @RequestMapping("/test")
    public String test()
    {
        return "hello word!";
    }
}
