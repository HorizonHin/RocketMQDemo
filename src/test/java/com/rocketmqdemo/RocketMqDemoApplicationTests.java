package com.rocketmqdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RocketMqDemoApplicationTests {

    @Autowired
    JMSConsumer consumer;
    @Autowired
    JMSProducer producer;
    @Test
    void contextLoads() {

    }

}
