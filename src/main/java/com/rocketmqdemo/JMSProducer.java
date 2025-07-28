package com.rocketmqdemo;

import com.rocketmqdemo.Util.JMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.net.URISyntaxException;

@Component
public class JMSProducer {

    public JMSProducer() {
    }

    @Autowired
    JMSUtil jmsUtil;

    public void sendMessage(String queueName, String message) throws JMSException, URISyntaxException {
        Connection connection = jmsUtil.getConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue(queueName);
//        Topic topic1 = session.createTopic(topic);
        TextMessage textMessage = session.createTextMessage(message);

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();
    }
}
