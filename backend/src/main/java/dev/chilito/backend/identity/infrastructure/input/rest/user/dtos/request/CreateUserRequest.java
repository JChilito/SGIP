package dev.chilito.backend.identity.infrastructure.input.rest.user.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message = "{user.email.notblank}")
    @Size(max = 150, message = "{user.email.size}")
    private String email;
    @NotBlank(message = "{user.firstName.notblank}")
    @Size(max = 100, message = "{user.firstName.size}")
    private String firstName;
    @NotBlank(message = "{user.lastName.notblank}")
    @Size(max = 100, message = "{user.lastName.size}")
    private String lastName;
    @NotBlank(message = "{user.firebaseUid.notblank}")
    private String firebaseUid;
}
