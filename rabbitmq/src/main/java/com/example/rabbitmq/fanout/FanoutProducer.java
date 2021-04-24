package com.example.rabbitmq.fanout;

import com.example.rabbitmq.model.MessageStruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Hodur
 * @since 2021-01-19
 */
@Component
public class FanoutProducer {

    @Autowired
    private RabbitTemplate template;

    @PostConstruct
    public void send() {

        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.schedule(() -> template
                .convertAndSend("amq.fanout", new MessageStruct(LocalTime.now().toString())), 30, TimeUnit.SECONDS);
    }
}
