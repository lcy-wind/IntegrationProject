package com.teamwork.integrationproject.common.rocketmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


public interface AccountProcessor
{
    @Output
    MessageChannel accountEvent();
}
