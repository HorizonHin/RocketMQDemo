package com.rocketmqdemo.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("exchange.fanout");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("exchange.direct");
    }

    private static class ReceiverConfig {

// AnonymousQueue:自动生成名称：队列名称由 RabbitMQ 自动分配，通常是一个随机字符串。
//                临时性：默认情况下是非持久、自动删除的队列，适合临时订阅或测试。
//                适用于广播场景：常用于 FanoutExchange，即发布/订阅模式中，每个消费者都有自己的匿名队列。
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue3() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(DirectExchange directExchange,
                                Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(directExchange).with("routingKey A");
        }

        @Bean
        public Binding binding2(DirectExchange directExchange,
                                Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(directExchange).with("routingKey B");
        }

        @Bean
        public Binding binding3(DirectExchange directExchange,
                                Queue autoDeleteQueue3) {
            return BindingBuilder.bind(autoDeleteQueue3).to(directExchange).with("routingKey C");
        }

        @Bean
        public RabbitReceiver receiver1() {
            return new RabbitReceiver(1);
        }

        @Bean
        public RabbitReceiver receiver2() {
            return new RabbitReceiver(2);
        }

    }

    @Bean
    public RabbitSender sender() {
        return new RabbitSender();
    }
}