package dev.chilito.backend.identity.infrastructure.output.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.chilito.backend.identity.application.output.IUserOutPort;
import dev.chilito.backend.identity.domain.models.User;
import dev.chilito.backend.identity.infrastructure.output.persistence.entities.UserEntity;
import dev.chilito.backend.identity.infrastructure.output.persistence.mappers.UserMapper;
import dev.chilito.backend.identity.infrastructure.output.persistence.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAdapter implements IUserOutPort {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public User saveUser(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDomain(savedUserEntity);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userMapper.toDomain(updatedUserEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userEntity.map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByFirebaseUid(String firebaseUid) {
        Optional<UserEntity> userEntity = userRepository.findByFirebaseUid(firebaseUid);
        return userEntity.map(userMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userMapper.toDomainList(userEntities);
    }

}
