package com.rocketmqdemo;

import com.rocketmqdemo.Util.JMSUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;


@Component
public class ActiveMQJMSConsumer {

    @Autowired
    JMSUtil jmsUtil;

    public void consume(String ClientID, String topicName, String subscriber) throws Exception{
        Connection connection = jmsUtil.createActiveMQConnection();
        connection.setClientID(ClientID);
        Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createDurableSubscriber(topic, subscriber);
        connection.start();
        Message message = consumer.receive();
        System.out.println("Received: " + ((TextMessage) message).getText());

        consumer.close();
        session.close();
        connection.close();
    }

}
