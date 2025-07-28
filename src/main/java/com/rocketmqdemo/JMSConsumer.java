package com.rocketmqdemo;

import com.rocketmqdemo.Util.JMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.net.URISyntaxException;

@Component
public class JMSConsumer {

    @Autowired
    private JMSUtil jmsUtil;

    public void consume(String queueName) throws JMSException, URISyntaxException {
        Connection connection = jmsUtil.getConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
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
