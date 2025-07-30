package com.rocketmqdemo.Util;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.rocketmq.jms.domain.CommonConstant;
import org.apache.rocketmq.jms.domain.JmsBaseConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.net.URI;

@Component
public class JMSUtil {

    @Value("${rocketmq.jms.nameServerAddr}")
    String nameServer;

    public Connection createRocketMQJMSConnection(String producerGroup, String consumerGroup, int consumeThreadNums) throws Exception {
        ConnectionFactory connectionFactory = new JmsBaseConnectionFactory(new
                URI(String.format("rocketmq://xxx?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                CommonConstant.PRODUCERID, producerGroup,
                CommonConstant.CONSUMERID, consumerGroup,
                CommonConstant.NAMESERVER, nameServer,
                CommonConstant.CONSUME_THREAD_NUMS, consumeThreadNums,
                CommonConstant.SEND_TIMEOUT_MILLIS, 10*1000,
                CommonConstant.INSTANCE_NAME, "JMS_TEST")));
        return  connectionFactory.createConnection();
    }

    public Connection createRocketMQJMSConnection(String producerGroup, String consumerGroup) throws Exception {
        ConnectionFactory connectionFactory = new JmsBaseConnectionFactory(new
                URI(String.format("rocketmq://xxx?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                CommonConstant.PRODUCERID, producerGroup,
                CommonConstant.CONSUMERID, consumerGroup,
                CommonConstant.NAMESERVER, nameServer,
                CommonConstant.SEND_TIMEOUT_MILLIS, 10*1000,
                CommonConstant.INSTANCE_NAME, "JMS_TEST")));
        return  connectionFactory.createConnection();
    }

//    public Connection createActiveMQConnection() throws JMSException {
//        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//        Connection connection = factory.createConnection();
//        return connection;
//    }

}
