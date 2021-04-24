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
 * @author shanghongcai
 */
@Slf4j
@RabbitListener(queues = "#{fanoutQueue2.name}")
@Component
public class FanoutTwoHandler {

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

        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("fanout queue 2, manual ack, receive message:{}", JSON.toJSONString(messageStruct));
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
