package com.rocketmqdemo.RocketMQJMS;

import com.rocketmqdemo.Util.JMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

@Component
public class RocketMQJMSConsumer {

    @Autowired
    private JMSUtil jmsUtil;

    Session session;
    MessageConsumer consumer;
    Connection connection;

    private CountDownLatch latch;

    public void consume(String producerID, String consumerID, String topicName) throws Exception {
        connection = jmsUtil.createRocketMQJMSConnection(producerID, consumerID);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(topicName);
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (latch != null) {
                    latch.countDown();
                }
                if (message instanceof TextMessage) {
                    try {
                        System.out.println("Received: " + ((TextMessage) message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Received non-text message");
                }
            }
        });
        connection.start(); // 别忘了启动连接
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public void close() throws JMSException {
        consumer.close();
        session.close();
        connection.close();
    }
}
