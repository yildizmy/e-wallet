package com.github.yildizmy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for Login request
 */
@Data
public class LoginRequest {

    @Size(min = 3, max = 20, message = "{username.size}")
    @NotBlank
    private String username;

    @Size(min = 6, max = 100, message = "{password.size}")
    @NotBlank
    private String password;
}
