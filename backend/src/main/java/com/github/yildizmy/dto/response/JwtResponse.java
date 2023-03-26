package com.github.yildizmy.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Data Transfer Object used for authentication response
 */
@Getter
@Builder
public class JwtResponse {

    private String type;
    private String token;
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
