package com.explore.backend.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import org.springframework.amqp.core.AcknowledgeMode;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String COMMAND_QUEUE = "command_queue_final";

    @Bean
    public Queue commandQueue() {
        return QueueBuilder.durable(COMMAND_QUEUE)
                .withArgument("x-message-ttl", 60000)
                .withArgument("x-consumer-auto-ack", true)
                .build();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        try {
            connectionFactory.getRabbitConnectionFactory().useSslProtocol();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException("SSL Protocol initialization failed", e);
        }
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        // For auto-acknowledgment behavior
        template.setMandatory(false);
        return template;
    }
}