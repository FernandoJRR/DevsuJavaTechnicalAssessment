package com.devsu.clientes.infrastructure.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClientResponse {
    private UUID clientId;
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
    private boolean active;
}
