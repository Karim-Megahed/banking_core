package com.example.bank.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MessagePublisher {
    private RabbitTemplate template;

    public void publishMessage(Object message) {
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);
    }
}