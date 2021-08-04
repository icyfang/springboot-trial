package com.example.rabbitmq.direct;

import com.example.rabbitmq.model.MessageStruct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RabbitListener(queues = "direct.queue.1")
@Component
public class DirectQueueTwoHandler {

    /**
     * auto ack
     *
     * @param message
     */
    // @RabbitHandler
    public void directHandlerAutoAck(MessageStruct message) throws JsonProcessingException {
        log.info("direct queue 1, receive message:{}", new ObjectMapper().writeValueAsBytes(message));
    }

    /**
     * manual ack
     *
     * @param messageStruct messageStruct
     * @param message       message
     * @param channel       channel
     */
    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel) {

        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("direct queue 2, manual ack, receive message:{}", new ObjectMapper().writeValueAsBytes(messageStruct));
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
