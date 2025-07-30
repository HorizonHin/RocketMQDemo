package com.rocketmqdemo.RocketMQJMS;

import com.rocketmqdemo.Util.JMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class RocketMQJMSConsumer {

    @Autowired
    private JMSUtil jmsUtil;

    public void consume(String producerID, String consumerID, String topicName) throws Exception {
        Connection connection = jmsUtil.createRocketMQJMSConnection(producerID, consumerID);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive(10);

        if (message instanceof TextMessage) {
            System.out.println("Received: " + ((TextMessage) message).getText());
        }else {
            System.out.println("Received non-text message");
        }

        consumer.close();
        session.close();
        connection.close();
    }
}
