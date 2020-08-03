package com.teamwork.integrationproject.common.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 扫描url配置
 */
@Component
//此注解只能扫描properties文件   如果使用yaml文件  只能扫描到application.yml文件 无法找到自定义yml文件
@PropertySource(value = {"classpath:apiUrl.properties"},ignoreResourceNotFound = false,encoding = "UTF-8",name = "apiUrl.properties")
@ConfigurationProperties(prefix = "api",ignoreUnknownFields=true,ignoreInvalidFields=true)
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
