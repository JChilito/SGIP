package dev.chilito.backend.identity.infrastructure.output.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.chilito.backend.identity.infrastructure.output.persistence.entities.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByFirebaseUid(String firebaseUid);
}
