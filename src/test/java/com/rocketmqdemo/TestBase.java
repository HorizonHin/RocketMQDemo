package com.rocketmqdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Random;

@Component
public class TestBase {

    protected static Random random = new Random();
    protected static final String SEP = File.separator;


    protected static String topic = "jms-test";
    protected static String topic2 = "jms-test-2";
    protected static String messageType = "TagA";
    protected static String producerId = "PID-jms-test";
    protected static String consumerId = "CID-jms-test";
    protected static String consumerId2 = "CID-jms-test-2";
    @Value("${rocketmq.jms.nameServerAddr}")
    protected String nameServer ;
    protected static String text = "English test";


    protected static int consumeThreadNums = 16;
}
