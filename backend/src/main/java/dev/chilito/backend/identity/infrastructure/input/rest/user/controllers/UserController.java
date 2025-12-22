package dev.chilito.backend.identity.infrastructure.input.rest.user.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.chilito.backend.identity.application.input.IUserInPort;
import dev.chilito.backend.identity.domain.models.User;
import dev.chilito.backend.identity.infrastructure.input.rest.user.dtos.request.CreateUserRequest;
import dev.chilito.backend.identity.infrastructure.input.rest.user.dtos.request.UpdateUserRequest;
import dev.chilito.backend.identity.infrastructure.input.rest.user.dtos.response.UserResponse;
import dev.chilito.backend.identity.infrastructure.input.rest.user.mappers.UserRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserInPort userInPort;
    private final UserRestMapper userRestMapper;

    /**
     * Sync user data
     * Authenticated (Any authenticated user can create or update their own data)
     * 
     * @param request
     * @return UserResponse
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> registerOrUpdateUser(@Valid @RequestBody CreateUserRequest request) {
        User userDomain = this.userRestMapper.toDomain(request);
        User userSaved = this.userInPort.registerOrUpdateUser(userDomain);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userRestMapper.toResponse(userSaved));
    }

    /**
     * Update user data
     * Authenticated (Only admins can update any user data)
     * 
     * @param id
     * @param request
     * @return UserResponse
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        User userDomain = this.userRestMapper.toDomain(request);
        User updatedUser = this.userInPort.updateUser(userDomain, id);
        return ResponseEntity.ok(this.userRestMapper.toResponse(updatedUser));
    }

    /**
     * Deactivate user
     * Authenticated (Only admins can deactivate any user)
     * 
     * @param id
     * @return void
     */
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        this.userInPort.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Activate user
     * Authenticated (Only admins can activate any user)
     * 
     * @param id
     * @return void
     */
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        this.userInPort.activateUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Get user by id
     * Authenticated (Only admins can get any user)
     * 
     * @param id
     * @return UserResponse
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User userDomain = this.userInPort.getUserById(id);
        return ResponseEntity.ok(this.userRestMapper.toResponse(userDomain));
    }

    /**
     * Get all users
     * Authenticated (Only admins can get all users)
     * 
     * @return List<UserResponse>
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> usersDomain = this.userInPort.getAllUsers();
        return ResponseEntity.ok(this.userRestMapper.toResponseList(usersDomain));
    }

    /**
     * Get profile
     * Authenticated (Any authenticated user can get their own profile)
     * 
     * @return UserResponse
     */
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal String uid) {
        User userDomain = this.userInPort.getUserByFirebaseUid(uid);
        return ResponseEntity.ok(this.userRestMapper.toResponse(userDomain));
    }

}
