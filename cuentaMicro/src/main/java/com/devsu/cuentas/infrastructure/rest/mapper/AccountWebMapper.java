package com.devsu.cuentas.infrastructure.rest.mapper;

import com.devsu.cuentas.domain.model.Account;
import com.devsu.cuentas.infrastructure.rest.dto.AccountRequest;
import com.devsu.cuentas.infrastructure.rest.dto.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountWebMapper {
    Account toDomain(AccountRequest request);
    AccountResponse toResponse(Account account);
}
