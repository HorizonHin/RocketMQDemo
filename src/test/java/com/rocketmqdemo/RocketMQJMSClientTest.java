package com.rocketmqdemo;

import com.rocketmqdemo.RocketMQJMS.RocketMQJMSConsumer;
import com.rocketmqdemo.RocketMQJMS.RocketMQJMSProducer;
import com.rocketmqdemo.util.JmsTestListener;
import org.apache.rocketmq.jms.domain.CommonConstant;
import org.apache.rocketmq.jms.domain.JmsBaseConnectionFactory;

import javax.jms.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import  java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static com.rocketmqdemo.TestBase.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RocketMQJMSClientTest{
    @Value("${rocketmq.jms.nameServerAddr}")
    protected  String nameServer ;

    @Autowired
    RocketMQJMSProducer producer;
    @Autowired
    RocketMQJMSConsumer consumer;

    private Connection createConnection(String producerGroup, String consumerGroup) throws JMSException, URISyntaxException {
        JmsBaseConnectionFactory connectionFactory = new JmsBaseConnectionFactory(new
                URI(String.format("rocketmq://xxx?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                CommonConstant.PRODUCERID, producerGroup,
                CommonConstant.CONSUMERID, consumerGroup,
                CommonConstant.NAMESERVER, nameServer,
                CommonConstant.CONSUME_THREAD_NUMS, consumeThreadNums,
                CommonConstant.SEND_TIMEOUT_MILLIS, 10*1000,
                CommonConstant.INSTANCE_NAME, "JMS_TEST")));
        return  connectionFactory.createConnection();
    }


    @Test
    public void createConnectionWithoutCPID_shouldThrowException() {
        assertThrows(Exception.class, () -> {
            URI uri = new URI(String.format("rocketmq://xxx?%s=%s&%s=%s&%s=%s&%s=%s",
                    CommonConstant.NAMESERVER, nameServer,
                    CommonConstant.CONSUME_THREAD_NUMS, consumeThreadNums,
                    CommonConstant.SEND_TIMEOUT_MILLIS, 10 * 1000,
                    CommonConstant.INSTANCE_NAME, "JMS_TEST"));
            new JmsBaseConnectionFactory(uri);
        });
    }



    @Test
    public void testSendMessage() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        consumer.setLatch(latch);

        String message = "Hello RocketMQ";
        consumer.consume(producerId,consumerId,topic2);
        producer.sendMessage(producerId, consumerId,topic2, message);
         boolean received = latch.await(5,TimeUnit.SECONDS);

         consumer.close();
         Assertions.assertTrue(received,"dont ceceived");
    }


    @Test
    public void testProducerAndConsume_TwoConsumer() throws Exception {

        Connection connection = createConnection(producerId, consumerId);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destinationA = session.createTopic("TopicA");
        Destination destinationB = session.createTopic("TopicB");
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        JmsTestListener listenerA = new JmsTestListener(10,countDownLatch);
        JmsTestListener listenerB = new JmsTestListener(10, countDownLatch);

        try {
            //two consumers
            MessageConsumer messageConsumerA = session.createConsumer(destinationA);
            messageConsumerA.setMessageListener(listenerA);
            MessageConsumer messageConsumerB = session.createConsumer(destinationB);
            messageConsumerB.setMessageListener(listenerB);
            //producer
            MessageProducer messageProducer = session.createProducer(destinationA);
            connection.start();

            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage(text + i);
                Assertions.assertNull(message.getJMSMessageID());
                messageProducer.send(message);
                Assertions.assertNotNull(message.getJMSMessageID());
            }
            for (int i = 0; i < 10; i++) {
                TextMessage message = session.createTextMessage(text + i);
                Assertions.assertNull(message.getJMSMessageID());
                messageProducer.send(destinationB, message);
                Assertions.assertNotNull(message.getJMSMessageID());
            }

            if (countDownLatch.await(10, TimeUnit.SECONDS)) {
                Thread.sleep(2000);
            }
            Assertions.assertEquals(10, listenerA.getConsumedNum());
            Assertions.assertEquals(10, listenerB.getConsumedNum());
        }
        finally {
            //Close the connection
            connection.close();
        }

    }

}
