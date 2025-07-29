package com.rocketmqdemo.Integration;

import java.io.File;
import java.util.Random;

public class IntegrationTestBase {

    protected static Random random = new Random();
    protected static final String SEP = File.separator;


    protected static String topic = "jms-test";
    protected static String topic2 = "jms-test-2";
    protected static String messageType = "TagA";
    protected static String producerId = "PID-jms-test";
    protected static String consumerId = "CID-jms-test";
    protected static String consumerId2 = "CID-jms-test-2";
    protected static String nameServer = "172.29.224.22:9876";
    protected static String text = "English test";


    protected static int consumeThreadNums = 16;
}
