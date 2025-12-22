package dev.chilito.backend.identity.application.output;

import java.util.List;
import java.util.Optional;

import dev.chilito.backend.identity.domain.models.User;

public interface IUserOutPort {
    User saveUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByFirebaseUid(String firebaseUid);

    List<User> getAllUsers();
}
