package com.devsu.cuentas.infrastructure.persistence.repository;

import com.devsu.cuentas.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {
    List<AccountEntity> findByClientId(UUID clientId);
}
