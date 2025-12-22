package dev.chilito.backend.identity.application.usecases;

import java.util.List;
import java.util.Optional;

import dev.chilito.backend.identity.application.input.IUserInPort;
import dev.chilito.backend.identity.application.output.IUserOutPort;
import dev.chilito.backend.identity.domain.models.Role;
import dev.chilito.backend.identity.domain.models.User;
import dev.chilito.backend.shared.domain.exception.BusinessException;
import dev.chilito.backend.shared.domain.exception.ErrorCode;

public class UserService implements IUserInPort {

    private final IUserOutPort userOutPort;

    public UserService(IUserOutPort userOutPort) {
        this.userOutPort = userOutPort;
    }

    /**
     * Register or update a user.
     * This method registers the user within the system, in case it already exists
     * it updates some information with the aim of having the data coming from
     * firebase updated in the system
     * 
     * @param user the user to register or update
     * @return the registered or updated user
     */
    @Override
    public User registerOrUpdateUser(User user) {
        Optional<User> existingUserOpt = this.userOutPort.getUserByFirebaseUid(user.getFirebaseUid());
        // If the user already exists, update it
        if (existingUserOpt.isPresent()) {
            User userExisting = existingUserOpt.get();
            userExisting.setEmail(user.getEmail());
            userExisting.setFirstName(user.getFirstName());
            userExisting.setLastName(user.getLastName());
            return this.userOutPort.updateUser(userExisting);
        } else {
            // If the user does not exist, create it
            user.setRole(Role.OPERATOR);
            user.setActive(true);
            return this.userOutPort.saveUser(user);
        }
    }

    @Override
    public User updateUser(User user, Long id) {
        User existingUser = this.getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        return this.userOutPort.updateUser(existingUser);
    }

    @Override
    public void deactivateUser(Long id) {
        User existingUser = this.getUserById(id);
        existingUser.setActive(false);
        this.userOutPort.updateUser(existingUser);
    }

    @Override
    public void activateUser(Long id) {
        User existingUser = this.getUserById(id);
        existingUser.setActive(true);
        this.userOutPort.updateUser(existingUser);
    }

    @Override
    public User getUserById(Long id) {
        return this.userOutPort.getUserById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "User with id " + id + " does not exist"));
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userOutPort.getUserByEmail(email).orElseThrow(
                () -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "User with email " + email + " does not exist"));
    }

    @Override
    public User getUserByFirebaseUid(String firebaseUid) {
        return this.userOutPort.getUserByFirebaseUid(firebaseUid).orElseThrow(
                () -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "User with firebase uid " + firebaseUid + " does not exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return this.userOutPort.getAllUsers();
    }

}
