package com.devsu.cuentas.infrastructure.rest.controller;

import com.devsu.cuentas.application.port.in.TransactionUseCase;
import com.devsu.cuentas.domain.model.Transaction;
import com.devsu.cuentas.infrastructure.rest.dto.TransactionRequest;
import com.devsu.cuentas.infrastructure.rest.dto.TransactionResponse;
import com.devsu.cuentas.infrastructure.rest.mapper.TransactionWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionUseCase transactionUseCase;
    private final TransactionWebMapper mapper;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        Transaction created = transactionUseCase.create(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> findAll() {
        List<TransactionResponse> list = transactionUseCase.findAll().stream()
                .map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponse(transactionUseCase.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> update(@PathVariable UUID id,
                                                       @Valid @RequestBody TransactionRequest request) {
        Transaction updated = transactionUseCase.update(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionResponse> partialUpdate(@PathVariable UUID id,
                                                              @RequestBody TransactionRequest request) {
        Transaction updated = transactionUseCase.partialUpdate(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }
}
