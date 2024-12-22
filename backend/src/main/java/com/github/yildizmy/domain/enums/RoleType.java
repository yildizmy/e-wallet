package com.github.yildizmy.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private String label;
}
