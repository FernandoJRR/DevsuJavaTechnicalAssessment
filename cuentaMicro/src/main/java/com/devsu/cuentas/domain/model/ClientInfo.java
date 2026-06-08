package com.devsu.cuentas.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ClientInfo {
    private UUID clientId;
    private String name;
    private boolean active;
}
