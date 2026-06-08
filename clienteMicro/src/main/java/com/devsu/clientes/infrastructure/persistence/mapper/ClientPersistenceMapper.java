package com.devsu.clientes.infrastructure.persistence.mapper;

import com.devsu.clientes.domain.model.Client;
import com.devsu.clientes.infrastructure.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {

    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.gender", target = "gender")
    @Mapping(source = "person.age", target = "age")
    @Mapping(source = "person.identification", target = "identification")
    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "person.phone", target = "phone")
    Client toDomain(ClientEntity entity);

    @Mapping(source = "name", target = "person.name")
    @Mapping(source = "gender", target = "person.gender")
    @Mapping(source = "age", target = "person.age")
    @Mapping(source = "identification", target = "person.identification")
    @Mapping(source = "address", target = "person.address")
    @Mapping(source = "phone", target = "person.phone")
    ClientEntity toEntity(Client domain);
}
