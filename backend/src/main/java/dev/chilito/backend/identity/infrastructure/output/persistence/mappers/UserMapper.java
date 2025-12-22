package dev.chilito.backend.identity.infrastructure.output.persistence.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import dev.chilito.backend.identity.domain.models.User;
import dev.chilito.backend.identity.infrastructure.output.persistence.entities.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    /**
     * Map a object User to a object UserEntity
     *
     * @param user
     * @return UserEntity
     */
    UserEntity toEntity(User user);

    /**
     * Map a object UserEntity to a object User
     *
     * @param entity
     * @return User
     */
    User toDomain(UserEntity entity);

    /**
     * Map a list of objects UserEntity to a list of objects User
     *
     * @param entities
     * @return List<User>
     */
    List<User> toDomainList(List<UserEntity> entities);
}
