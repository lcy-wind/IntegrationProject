package com.teamwork.integrationproject.common.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Author anyho
 * Time   2020/3/19 14:52 星期四
 */
@Component
@ConfigurationProperties(prefix = "api")
@Data
public class ApiProperties
{
    private String name;

    private Map<String, String> urlMap;

    public String getURL(String apiKey)
    {
        Assert.state(urlMap.containsKey(apiKey), apiKey + "的URL没有配置!");

        return urlMap.get(apiKey);
    }
}
