package com.rocketmqdemo;

import org.apache.rocketmq.jms.domain.JmsBaseConnectionFactory;

import javax.jms.*;
import java.net.URI;
import java.net.URISyntaxException;

public class JMSProducer {

    public JMSProducer(){

    }

    public void sendMessage(String topic, String message) throws JMSException, URISyntaxException {
        ConnectionFactory connectionFactory = new JmsBaseConnectionFactory(new URI("tcp://localhost:9876"));
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue("queue/testQueue");
//        Topic topic1 = session.createTopic(topic);
        TextMessage textMessage = session.createTextMessage(message);

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(textMessage);


    }
}
