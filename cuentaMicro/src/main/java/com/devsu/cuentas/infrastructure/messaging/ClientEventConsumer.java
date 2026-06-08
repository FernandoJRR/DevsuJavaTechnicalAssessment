package com.devsu.cuentas.infrastructure.messaging;

import com.devsu.cuentas.application.port.in.ClientInfoUseCase;
import com.devsu.cuentas.infrastructure.messaging.config.RabbitConfig;
import com.devsu.cuentas.infrastructure.messaging.dto.ClientEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientEventConsumer {

    private final ClientInfoUseCase clientInfoUseCase;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handleClientEvent(ClientEvent event) {
        log.info("Received client event: type={}, clientId={}", event.getEventType(), event.getClientId());

        switch (event.getEventType()) {
            case CREATED, UPDATED ->
                clientInfoUseCase.upsert(event.getClientId(), event.getName(), event.isActive());
            case DELETED -> clientInfoUseCase.markInactive(event.getClientId());
        }
    }
}
