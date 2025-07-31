package com.rocketmqdemo;


import com.rocketmqdemo.RabbitMQ.RabbitSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitTest {

    @Autowired
    RabbitSender sender;

    @Test
    public void testSendAndReceived() throws InterruptedException {
        //模拟定时任务
        for (int i = 0; i < 10; i++) {
            sender.send();
            Thread.sleep(1000); // 模拟 fixedDelay
        }
    }
}
