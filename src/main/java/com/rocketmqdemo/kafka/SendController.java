package com.rocketmqdemo.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class SendController {

    @Autowired
    MyKafkaProducer producer;

    @GetMapping
    public String sendMessage(@RequestParam String msg){
        producer.sendMessage("topic1",msg);
        return  "Message sent: "+msg;
    }
}
