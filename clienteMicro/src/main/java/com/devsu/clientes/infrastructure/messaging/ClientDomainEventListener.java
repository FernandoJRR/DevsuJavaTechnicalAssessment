package com.devsu.clientes.infrastructure.messaging;

import com.devsu.clientes.application.event.ClientDomainEvent;
import com.devsu.clientes.application.port.out.ClientEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ClientDomainEventListener {

    private final ClientEventPublisherPort eventPublisherPort;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ClientDomainEvent event) {
        switch (event.getType()) {
            case CREATED -> eventPublisherPort.publishCreated(event.getClient());
            case UPDATED -> eventPublisherPort.publishUpdated(event.getClient());
            case DELETED -> eventPublisherPort.publishDeleted(event.getClientId());
        }
    }
}
