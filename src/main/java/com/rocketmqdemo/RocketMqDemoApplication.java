package com.rocketmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = "com.rocketmqdemo")
public class RocketMqDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketMqDemoApplication.class, args);
    }

}
