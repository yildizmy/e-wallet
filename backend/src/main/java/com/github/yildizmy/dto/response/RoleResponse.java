package com.github.yildizmy.dto.response;

import com.github.yildizmy.domain.enums.RoleType;
import lombok.Data;

/**
 * Data Transfer Object for Role response.
 */
@Data
public class RoleResponse {

    private Long id;
    private RoleType type;
}
