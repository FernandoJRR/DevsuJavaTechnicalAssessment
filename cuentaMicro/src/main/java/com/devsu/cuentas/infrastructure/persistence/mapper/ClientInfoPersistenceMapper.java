package com.devsu.cuentas.infrastructure.persistence.mapper;

import com.devsu.cuentas.domain.model.ClientInfo;
import com.devsu.cuentas.infrastructure.persistence.entity.ClientInfoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientInfoPersistenceMapper {
    ClientInfo toDomain(ClientInfoEntity entity);
    ClientInfoEntity toEntity(ClientInfo domain);
}
