package com.teamwork.integrationproject.controller;

import com.teamwork.integrationproject.common.rocketmq.AccountProcessor;
import com.teamwork.integrationproject.common.rocketmq.AccountStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2020/11/02 11:43
 */
@RestController
@RequestMapping("v1")
public class RocketMQController
{
    @Autowired
    private AccountStreamService accountEvent;
    @GetMapping("send")
    public boolean send(){
      return accountEvent.send("hello---->>> rocketmq");
    }
}