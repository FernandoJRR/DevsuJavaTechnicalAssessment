package com.devsu.clientes.infrastructure.messaging.mapper;

import com.devsu.clientes.domain.model.Client;
import com.devsu.clientes.infrastructure.messaging.dto.ClientEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientEventMapper {

    @Mapping(target = "eventType", ignore = true)
    ClientEvent toEvent(Client client);
}
