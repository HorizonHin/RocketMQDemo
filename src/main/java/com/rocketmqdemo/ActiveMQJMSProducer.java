package com.rocketmqdemo;

import com.rocketmqdemo.Util.JMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class ActiveMQJMSProducer {

    @Autowired
    JMSUtil jmsUtil;

    public void sendMessage(String topicName,String  message) throws Exception{
        Connection connection = jmsUtil.createActiveMQConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(destination);
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();
    }
}
