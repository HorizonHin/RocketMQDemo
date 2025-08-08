package com.rocketmqdemo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class MyKafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String msg)  {
        kafkaTemplate.send(topic, msg);
        System.out.println("Sent: " + msg);
    }
}
