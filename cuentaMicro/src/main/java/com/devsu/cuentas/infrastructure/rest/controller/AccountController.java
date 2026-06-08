package com.devsu.cuentas.infrastructure.rest.controller;

import com.devsu.cuentas.application.port.in.AccountUseCase;
import com.devsu.cuentas.domain.model.Account;
import com.devsu.cuentas.infrastructure.rest.dto.AccountRequest;
import com.devsu.cuentas.infrastructure.rest.dto.AccountResponse;
import com.devsu.cuentas.infrastructure.rest.mapper.AccountWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUseCase accountUseCase;
    private final AccountWebMapper mapper;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        Account created = accountUseCase.create(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> list = accountUseCase.findAll().stream()
                .map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> findById(@PathVariable String accountNumber) {
        return ResponseEntity.ok(mapper.toResponse(accountUseCase.findById(accountNumber)));
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> update(@PathVariable String accountNumber,
                                                   @Valid @RequestBody AccountRequest request) {
        Account updated = accountUseCase.update(accountNumber, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> partialUpdate(@PathVariable String accountNumber,
                                                          @RequestBody AccountRequest request) {
        Account updated = accountUseCase.partialUpdate(accountNumber, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }
}
