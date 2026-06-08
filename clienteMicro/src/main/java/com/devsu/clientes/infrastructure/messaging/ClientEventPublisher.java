package com.devsu.clientes.infrastructure.messaging;

import com.devsu.clientes.application.port.out.ClientEventPublisherPort;
import com.devsu.clientes.domain.model.Client;
import com.devsu.clientes.infrastructure.config.RabbitConfig;
import com.devsu.clientes.infrastructure.messaging.dto.ClientEvent;
import com.devsu.clientes.infrastructure.messaging.mapper.ClientEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientEventPublisher implements ClientEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;
    private final ClientEventMapper mapper;

    @Override
    public void publishCreated(Client client) {
        ClientEvent event = mapper.toEvent(client);
        event.setEventType(EventType.CREATED);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.KEY_CREATED, event);
    }

    @Override
    public void publishUpdated(Client client) {
        ClientEvent event = mapper.toEvent(client);
        event.setEventType(EventType.UPDATED);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.KEY_UPDATED, event);
    }

    @Override
    public void publishDeleted(UUID clientId) {
        ClientEvent event = new ClientEvent(EventType.DELETED, clientId, null, false);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.KEY_DELETED, event);
    }
}
