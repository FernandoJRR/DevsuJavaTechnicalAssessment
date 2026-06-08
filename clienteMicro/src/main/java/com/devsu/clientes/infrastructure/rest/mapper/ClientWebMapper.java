package com.devsu.clientes.infrastructure.rest.mapper;

import com.devsu.clientes.domain.model.Client;
import com.devsu.clientes.infrastructure.rest.dto.ClientRequest;
import com.devsu.clientes.infrastructure.rest.dto.ClientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientWebMapper {
    Client toDomain(ClientRequest request);
    ClientResponse toResponse(Client client);
}
