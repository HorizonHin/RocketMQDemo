package com.rocketmqdemo;

import com.rocketmqdemo.Util.JMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
@Component
public class RocketMQJMSProducer {

    public RocketMQJMSProducer() {
    }

    @Autowired
    JMSUtil jmsUtil;

    public void sendMessage(String produceID, String consumerID, String topicName, String message) throws Exception {
        Connection connection = jmsUtil.createRocketMQJMSConnection(produceID,consumerID);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createTopic(topicName);
        TextMessage textMessage = session.createTextMessage(message);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();
    }
}
