package com.teamwork.integrationproject.common.instrument;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class TemplateUtils
{

    //引用此注解必须要进行bean配置否则报错   在config  进行bean配置
    @Autowired
    private RestTemplate restTemplate;

    /**
     * get
     *
     * @param url         请求地址
     * @param param       参数
     * @param returnClass 返回类型
     * @return
     */
    public <T> T get(String url, Class<T> returnClass, Map<String, ?> param) {
        return restTemplate.getForObject(url, returnClass, param);
    }

    /**
     * post
     *
     * @param url         请求地址
     * @param param       参数
     * @param returnClass 返回类型
     * @param header      自定义的头信息
     * @return
     */
    public <E> E post(String url, E param, Class<E> returnClass, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            headers.set(entry.getKey(), entry.getValue());
        }

        //header.forEach((o1, o2) -> headers.set(o1, o2));
        HttpEntity<E> httpEntity = new HttpEntity<E>(param, headers);
        return restTemplate.postForObject(url, httpEntity, returnClass);
    }

    /**
     * post
     *
     * @param url         请求地址
     * @param param       参数
     * @param returnClass 返回类型
     * @return
     */
    public <E> E postByDefault(String url, E param, Class<E> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        HttpEntity<E> httpEntity = new HttpEntity<E>(param, headers);
        return restTemplate.postForObject(url, httpEntity, returnClass);
    }

    /**
     * post方式，json格式传输和接收数据
     * @param url
     * @param param
     * @return
     */
    public String postByDefault(String url, String param)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(param, headers);
        String jsonResult = restTemplate.postForObject(url , formEntity, String.class);
        return jsonResult;
    }

    /**
     * get方式，json格式接收数据
     * @param url
     * @return
     */
    public String getByDefault(String url)
    {
        String jsonResult = restTemplate.getForObject(url , String.class);
        return jsonResult;
    }

}
