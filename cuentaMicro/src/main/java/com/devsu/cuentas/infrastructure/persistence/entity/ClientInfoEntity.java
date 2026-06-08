package com.devsu.cuentas.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "clients_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoEntity {

    @Id
    private UUID clientId;

    private String name;

    private boolean active;
}
