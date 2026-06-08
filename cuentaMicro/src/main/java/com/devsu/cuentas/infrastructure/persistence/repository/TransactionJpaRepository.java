package com.devsu.cuentas.infrastructure.persistence.repository;

import com.devsu.cuentas.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByAccountNumberAndDateBetween(String accountNumber,
                                                               LocalDateTime start,
                                                               LocalDateTime end);
}
