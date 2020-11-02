package com.teamwork.integrationproject.common.rocketmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Author anyho
 * Time   2020/3/24 11:45 星期二
 */
public interface AccountProcessor
{
    @Output
    MessageChannel accountEvent();
}
