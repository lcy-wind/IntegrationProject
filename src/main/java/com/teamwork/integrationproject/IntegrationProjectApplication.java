package com.teamwork.integrationproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.teamwork.integrationproject.mapper")
@ComponentScan(basePackages = {"com.teamwork"})
@EnableScheduling
//集成该类，重写方法用于springboot项目部署
public class IntegrationProjectApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(IntegrationProjectApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IntegrationProjectApplication.class);
    }
}
