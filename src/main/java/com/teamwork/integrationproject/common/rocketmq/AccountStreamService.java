package com.teamwork.integrationproject.common.rocketmq;

import com.teamwork.integrationproject.utils.log.LogHelper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Author anyho
 * Time   2020/3/24 12:17 星期二
 */
@Service
@AllArgsConstructor
public class AccountStreamService
{
    private AccountProcessor processor;

//    public <T> boolean send(T payload)
//    {
//        boolean send = processor.accountEvent().send(MessageBuilder.withPayload(payload).build());
//        LogHelper.info(this, "send message: {}", send);
//        return send;
//    }

    public  boolean send(String name)
    {
        boolean send = processor.accountEvent().send(MessageBuilder.withPayload(name).build());
        LogHelper.info(this, "send message: {}", send);
        return send;
    }
}
