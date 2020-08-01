package com.teamwork.integrationproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.teamwork.integrationproject.userAggregate")
@ComponentScan("com.teamwork")
@EnableScheduling
public class IntegrationProjectApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(IntegrationProjectApplication.class, args);
    }

}
