package com.github.yildizmy.dto.response;

import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for User response
 */
@Data
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String fullName;
    private Set<RoleResponse> roles;
}
