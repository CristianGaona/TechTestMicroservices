package com.example.clientmanagement.infrastructure.output.adapter.config.rabbit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.AmqpTemplate;

@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    @Primary
    public AmqpTemplate rabbitTemplateCustom(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setReplyTimeout(TimeUnit.SECONDS.toMillis(20));
        rabbitTemplate.setReceiveTimeout(TimeUnit.SECONDS.toMillis(20));
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }


    //========================ACCOUNT====================================
    @Bean
    public TopicExchange accountExchange() {
        return new TopicExchange("exchange.account.client", true, false);
    }


    // QUEUE 1
    @Bean
    public Queue clientsInfoQueue() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-message-ttl", 10000);
        return new Queue("queue.account.client.info", true, false, false, arguments);
    }

    @Bean
    public Binding clientsInfoBinding(Queue clientsInfoQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(clientsInfoQueue).to(accountExchange).with("routing.account.client.info");
    }

    // QUEUE 2

    @Bean
    public Queue clientExistsQueue() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-message-ttl", 10000);
        return new Queue("queue.client.exists", true, false, false, arguments);
    }

    @Bean
    public Binding clientExistsBinding(Queue clientExistsQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(clientExistsQueue).to(accountExchange).with("routing.client.exists");
    }

    // QUEUE 3
    @Bean
    public Queue accountExistsQueue() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-message-ttl", 10000);
        return new Queue("queue.account.exists", true, false, false, arguments);
    }

    @Bean
    public Binding accountExistsbinding(Queue accountExistsQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(accountExistsQueue).to(accountExchange).with("routing.account.exists");
    }
}
