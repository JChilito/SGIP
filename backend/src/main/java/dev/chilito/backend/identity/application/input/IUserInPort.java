package dev.chilito.backend.identity.application.input;

import java.util.List;

import dev.chilito.backend.identity.domain.models.User;

public interface IUserInPort {
    User registerOrUpdateUser(User user);

    User updateUser(User user, Long id);

    void deactivateUser(Long id);

    void activateUser(Long id);

    User getUserById(Long id);

    User getUserByEmail(String email);

    User getUserByFirebaseUid(String firebaseUid);

    List<User> getAllUsers();
}
