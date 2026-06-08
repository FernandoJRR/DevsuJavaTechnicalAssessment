package com.devsu.clientes.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class ClientTest {

    @InjectMocks
    private Client client;

    @Test
    @DisplayName("Given a new Client, When no active flag is set, Then active defaults to true")
    void activeDefaultsToTrue() {
        // Given

        // When
        Boolean active = client.getActive();

        // Then
        then(active).isTrue();
    }

    @Test
    @DisplayName("Given a new Client, When name and age are set, Then they are retrievable via Person fields")
    void inheritsPersonFields() {
        // Given
        client.setName("John");
        client.setAge(30);

        // When
        String name = client.getName();
        int age = client.getAge();

        // Then
        then(name).isEqualTo("John");
        then(age).isEqualTo(30);
    }

    @Test
    @DisplayName("Given a new Client, When a password is assigned, Then it is stored correctly")
    void passwordFieldAssignedCorrectly() {
        // Given
        String rawPassword = "hashed_value";

        // When
        client.setPassword(rawPassword);

        // Then
        then(client.getPassword()).isEqualTo("hashed_value");
    }
}
