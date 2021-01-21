package com.teamwork.integrationproject.common.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class SnowFlakeInitializer implements ApplicationRunner
{

    @Value("${snowflake.datacenter-id:0}")
    long datacenterId;

    @Value("${snowflake.machine-id:0}")
    long machineId;

    @Override
    public void run(ApplicationArguments arguments)
    {
        //根据配置文件实例SnowFlake
        SnowFlake.init(datacenterId, machineId);
    }
}
