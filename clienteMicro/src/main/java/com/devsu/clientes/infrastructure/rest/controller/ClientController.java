package com.devsu.clientes.infrastructure.rest.controller;

import com.devsu.clientes.application.port.in.ClientUseCase;
import com.devsu.clientes.domain.model.Client;
import com.devsu.clientes.infrastructure.rest.dto.ClientRequest;
import com.devsu.clientes.infrastructure.rest.dto.ClientResponse;
import com.devsu.clientes.infrastructure.rest.mapper.ClientWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientUseCase clientUseCase;
    private final ClientWebMapper mapper;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest request) {
        Client created = clientUseCase.create(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        List<ClientResponse> list = clientUseCase.findAll().stream()
                .map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponse(clientUseCase.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody ClientRequest request) {
        Client updated = clientUseCase.update(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> partialUpdate(@PathVariable UUID id,
                                                         @RequestBody ClientRequest request) {
        Client updated = clientUseCase.partialUpdate(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
