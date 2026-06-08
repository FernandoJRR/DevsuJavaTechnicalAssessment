package com.devsu.cuentas.infrastructure.persistence.mapper;

import com.devsu.cuentas.domain.model.Transaction;
import com.devsu.cuentas.infrastructure.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionPersistenceMapper {
    Transaction toDomain(TransactionEntity entity);
    TransactionEntity toEntity(Transaction domain);
}
