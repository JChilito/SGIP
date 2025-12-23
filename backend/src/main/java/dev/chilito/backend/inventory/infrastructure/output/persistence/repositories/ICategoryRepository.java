package dev.chilito.backend.inventory.infrastructure.output.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.chilito.backend.inventory.infrastructure.output.persistence.entities.CategoryEntity;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
