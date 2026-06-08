package com.devsu.clientes.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE    = "clients.exchange";
    public static final String QUEUE       = "clients.queue";
    public static final String KEY_CREATED = "client.created";
    public static final String KEY_UPDATED = "client.updated";
    public static final String KEY_DELETED = "client.deleted";

    @Bean
    TopicExchange clientsExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Queue clientsQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    Binding binding(Queue clientsQueue, TopicExchange clientsExchange) {
        return BindingBuilder.bind(clientsQueue).to(clientsExchange).with("client.*");
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                   Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
