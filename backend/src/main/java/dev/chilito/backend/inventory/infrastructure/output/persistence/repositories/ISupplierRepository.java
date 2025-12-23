package dev.chilito.backend.inventory.infrastructure.output.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.chilito.backend.inventory.infrastructure.output.persistence.entities.SupplierEntity;

public interface ISupplierRepository extends JpaRepository<SupplierEntity, Long> {

}
