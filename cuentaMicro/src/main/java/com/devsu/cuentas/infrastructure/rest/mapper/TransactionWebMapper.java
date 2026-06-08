package com.devsu.cuentas.infrastructure.rest.mapper;

import com.devsu.cuentas.domain.model.Transaction;
import com.devsu.cuentas.infrastructure.rest.dto.TransactionRequest;
import com.devsu.cuentas.infrastructure.rest.dto.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionWebMapper {
    Transaction toDomain(TransactionRequest request);
    TransactionResponse toResponse(Transaction transaction);
}
