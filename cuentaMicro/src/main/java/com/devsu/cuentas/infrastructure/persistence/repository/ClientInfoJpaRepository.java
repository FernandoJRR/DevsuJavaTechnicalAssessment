package com.devsu.cuentas.infrastructure.persistence.repository;

import com.devsu.cuentas.infrastructure.persistence.entity.ClientInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientInfoJpaRepository extends JpaRepository<ClientInfoEntity, UUID> {
    List<ClientInfoEntity> findByClientIdIn(List<UUID> clientIds);
}
