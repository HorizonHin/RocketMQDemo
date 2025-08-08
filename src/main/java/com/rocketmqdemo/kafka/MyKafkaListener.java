package com.rocketmqdemo.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class MyKafkaListener {

    CountDownLatch countDownLatch;

    @KafkaListener(topics = "topic1",groupId = "my-group")
    public void kafkaListener(ConsumerRecord<String,String> record) {
        if (countDownLatch!=null)
            countDownLatch.countDown();
        System.out.println("from  topics = \"topic1\" receive:  "+ record.value());
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
