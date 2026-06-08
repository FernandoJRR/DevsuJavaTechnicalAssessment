package com.devsu.clientes.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Client extends Person {
    private UUID clientId;
    private String password;
    private Boolean active = true;
}
