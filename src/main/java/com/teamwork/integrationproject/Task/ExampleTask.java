package com.teamwork.integrationproject.Task;

import com.teamwork.integrationproject.common.props.ApiProperties;
import com.teamwork.integrationproject.common.instrument.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExampleTask
{

    @Autowired
    private TemplateUtils restTemplateUtils;

    @Autowired
    private ApiProperties apiProperties;

//    @Scheduled(cron = "0/1 * * * * *")
    public void test()
    {
        String url = apiProperties.getURL("project");
        restTemplateUtils.getByDefault(url);
        System.out.println( "hello word!");
    }
}
