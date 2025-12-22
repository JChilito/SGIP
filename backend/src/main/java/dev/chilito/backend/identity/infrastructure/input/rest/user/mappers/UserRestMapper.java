package dev.chilito.backend.identity.infrastructure.input.rest.user.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import dev.chilito.backend.identity.domain.models.User;
import dev.chilito.backend.identity.infrastructure.input.rest.user.dtos.request.CreateUserRequest;
import dev.chilito.backend.identity.infrastructure.input.rest.user.dtos.request.UpdateUserRequest;
import dev.chilito.backend.identity.infrastructure.input.rest.user.dtos.response.UserResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRestMapper {

    /**
     * Maps a CreateUserRequest to a User
     * 
     * @param request
     * @return the User mapped from the CreateUserRequest
     */
    User toDomain(CreateUserRequest request);

    /**
     * Maps a UpdateUserRequest to a User
     * 
     * @param request
     * @return the User mapped from the UpdateUserRequest
     */
    User toDomain(UpdateUserRequest request);

    /**
     * Maps a User to a UserResponse
     * 
     * @param user
     * @return the UserResponse mapped from the User
     */
    UserResponse toResponse(User user);

    /**
     * Maps a list of Users to a list of UserResponses
     * 
     * @param users
     * @return the list of UserResponses mapped from the list of Users
     */
    List<UserResponse> toResponseList(List<User> users);
}
