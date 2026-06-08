package com.devsu.clientes.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEmbeddable {
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
