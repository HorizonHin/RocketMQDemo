package com.rocketmqdemo;

import com.rocketmqdemo.kafka.MyKafkaListener;
import com.rocketmqdemo.kafka.MyKafkaProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class kafkaTest {

    @Autowired
    MyKafkaProducer producer;

    @Autowired
    MyKafkaListener listener;

    @Test
    public void send() throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        listener.setCountDownLatch(latch);
        producer.sendMessage("topic1","hello kafka");
        boolean isCount = latch.await(5, TimeUnit.SECONDS);
        Assertions.assertTrue(isCount, "未收到消息");
    }
}
