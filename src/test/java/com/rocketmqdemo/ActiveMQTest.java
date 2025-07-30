package com.rocketmqdemo;

import com.rocketmqdemo.ActiveMQ.ActiveMQConsumer;
import com.rocketmqdemo.ActiveMQ.ActiveMQProducer;
import jakarta.jms.JMSException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ActiveMQTest {

    @Autowired
    private ActiveMQProducer producer;

    @Autowired
    private ActiveMQConsumer consumer;

    @Test
    public void testSendMessage() throws InterruptedException, JMSException {
        String destination = "demo.queue";
        String message = "Hello from ActiveMQ Test!";
        producer.sendMessage(destination, message);

        CountDownLatch latch = new CountDownLatch(1);
        consumer.setLatch(latch);
        consumer.consumeMessage(destination);
        boolean isReceived = latch.await(5, TimeUnit.SECONDS);
        Assertions.assertTrue(isReceived, "未收到消息");
    }
}
