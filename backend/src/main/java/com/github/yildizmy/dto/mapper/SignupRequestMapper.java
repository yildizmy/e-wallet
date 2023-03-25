package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.SignupRequest;
import com.github.yildizmy.model.Role;
import com.github.yildizmy.model.RoleType;
import com.github.yildizmy.model.User;
import com.github.yildizmy.service.RoleService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

/**
 * Mapper used for mapping SignupRequest fields
 */
@Mapper(componentModel = "spring",
        uses = {PasswordEncoder.class, RoleService.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class SignupRequestMapper {

    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Mapping(target = "firstName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getFirstName()))")
    @Mapping(target = "lastName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getLastName()))")
    @Mapping(target = "username", expression = "java(dto.getUsername().trim().toLowerCase())")
    @Mapping(target = "email", expression = "java(dto.getEmail().trim().toLowerCase())")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    public abstract User toEntity(SignupRequest dto);

    @AfterMapping
    void setToEntityFields(@MappingTarget User entity, SignupRequest dto) {
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        final List<RoleType> roleTypes = dto.getRoles().stream()
                .map(RoleType::valueOf)
                .toList();
        final List<Role> roles = roleService.getReferenceByTypeIsIn(new HashSet<>(roleTypes));
        entity.setRoles(new HashSet<>(roles));
    }
}
