package com.rocketmqdemo.RabbitMQ;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class RabbitSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    FanoutExchange fanoutExchange;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Autowired
    DirectExchange directExchange;

    private final String[] keys = {"routingKey A", "routingKey B", "routingKey C"};

    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots.incrementAndGet() == 3) {
            dots.set(0);
        }
        builder.append(".".repeat(Math.max(0, dots.get())));
        builder.append(count.incrementAndGet()); //给消息标号
        String message = builder.toString();
        template.convertAndSend(directExchange.getName(),keys[dots.get()], message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}