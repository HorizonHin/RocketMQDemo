package com.rocketmqdemo.ActiveMQ;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class ActiveMQConsumer {

    @Value("${activemq.broker-url}")
    private String brokerUrl;
    private  CountDownLatch latch ;

    public void consumeMessage(String queueName) throws JMSException {
            ConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive(); // 等待指定时间

            if (message instanceof TextMessage textMessage) {
                String result = textMessage.getText();
                System.out.println("Received from [" + queueName + "]: " + result);
            }

            if (latch !=null)
                latch.countDown();

            consumer.close();
            session.close();
            connection.close();

    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
