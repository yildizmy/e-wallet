package com.github.yildizmy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for signup request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private Long id;

    @Size(min = 3, max = 50, message = "{validation.user.firstname.length}")
    @NotBlank(message = "{validation.user.firstname.required}")
    private String firstName;

    @Size(min = 3, max = 50, message = "{validation.user.lastname.length}")
    @NotBlank(message = "{validation.user.lastname.required}")
    private String lastName;

    @Size(min = 3, max = 20, message = "{validation.user.username.length}")
    @NotBlank(message = "{validation.user.username.required}")
    private String username;

    @Email(message = "{validation.user.email.format}")
    @Size(min = 6, max = 50, message = "{validation.user.email.length}")
    @NotBlank(message = "{validation.user.email.required}")
    private String email;

    @Size(min = 6, max = 100, message = "{validation.user.password.length}")
    @NotBlank(message = "{validation.user.password.required}")
    private String password;

    private Set<String> roles;
}
