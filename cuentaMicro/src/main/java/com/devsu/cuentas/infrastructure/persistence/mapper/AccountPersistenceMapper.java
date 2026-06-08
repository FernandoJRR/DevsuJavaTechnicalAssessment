package com.devsu.cuentas.infrastructure.persistence.mapper;

import com.devsu.cuentas.domain.model.Account;
import com.devsu.cuentas.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountPersistenceMapper {
    Account toDomain(AccountEntity entity);
    AccountEntity toEntity(Account domain);
}
