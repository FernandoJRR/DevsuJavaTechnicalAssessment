package com.devsu.clientes.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    @NotNull(message = "El estado es obligatorio")
    private Boolean active;
}
