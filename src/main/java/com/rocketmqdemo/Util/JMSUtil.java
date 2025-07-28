package com.rocketmqdemo.Util;

import org.apache.rocketmq.jms.domain.JmsBaseConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class JMSUtil {


    @Value("rocketmq.jms.broker-uri")
    private  String brokerURI ;

    public  Connection getConnection() throws URISyntaxException, JMSException {
        ConnectionFactory connectionFactory = new JmsBaseConnectionFactory(new URI(brokerURI));
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }
}
