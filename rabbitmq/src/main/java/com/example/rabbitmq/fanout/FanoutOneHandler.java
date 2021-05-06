package com.example.rabbitmq.fanout;

import com.alibaba.fastjson.JSON;
import com.example.rabbitmq.model.MessageStruct;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Hodur
 */
@Slf4j
@RabbitListener(queues = "#{fanoutQueue1.name}")
@Component
public class FanoutOneHandler {

    /**
     * auto ack
     *
     * @param message
     */
    // @RabbitHandler
    public void directHandlerAutoAck(MessageStruct message) {
        log.info("direct queue 1, receive message:{}", JSON.toJSONString(message));
    }

    /**
     * manual ack
     *
     * @param messageStruct
     * @param message
     * @param channel
     */
    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("fanout queue 1, manual ack, receive message:{}", JSON.toJSONString(messageStruct));
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
