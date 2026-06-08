package com.devsu.cuentas.infrastructure.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "clients.exchange";
    public static final String QUEUE    = "clients.cuenta.queue";

    @Bean
    TopicExchange clientsExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Queue clientsCuentaQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    Binding binding(Queue clientsCuentaQueue, TopicExchange clientsExchange) {
        return BindingBuilder.bind(clientsCuentaQueue).to(clientsExchange).with("client.*");
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                   Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
