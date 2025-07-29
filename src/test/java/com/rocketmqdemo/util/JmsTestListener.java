package com.rocketmqdemo.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.junit.Assert;

public class JmsTestListener implements MessageListener {

    private int expectd;
    private CountDownLatch latch;
    private AtomicInteger consumedNum = new AtomicInteger(0);

    public JmsTestListener() {
        this.expectd = 10;
    }
    public JmsTestListener(int expectd) {
        this.expectd = expectd;
    }
    public JmsTestListener(int expected, CountDownLatch latch) {
        this.expectd = expected;
        this.latch = latch;
    }
    @Override
    public void onMessage(Message message) {
        try {
            Assert.assertNotNull(message);
            Assert.assertNotNull(message.getJMSMessageID());
            if (consumedNum.incrementAndGet() == expectd && latch != null) {
                latch.countDown();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getConsumedNum() {
        return consumedNum.get();
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public void setExpectd(int expectd) {
        this.expectd = expectd;
    }
}

