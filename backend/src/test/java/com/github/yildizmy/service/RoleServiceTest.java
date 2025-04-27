package com.github.yildizmy.service;

import com.github.yildizmy.domain.entity.Role;
import com.github.yildizmy.domain.enums.RoleType;
import com.github.yildizmy.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void getReferenceByTypeIsIn_shouldReturnRolesForGivenTypes() {
        var types = Set.of(RoleType.ROLE_ADMIN, RoleType.ROLE_USER);
        var expectedRoles = List.of(
                createRole(1L, RoleType.ROLE_ADMIN),
                createRole(2L, RoleType.ROLE_USER)
        );

        when(roleRepository.getReferenceByTypeIsIn(types)).thenReturn(expectedRoles);

        var result = roleService.getReferenceByTypeIsIn(types);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(role -> role.getType() == RoleType.ROLE_ADMIN));
        assertTrue(result.stream().anyMatch(role -> role.getType() == RoleType.ROLE_USER));

        verify(roleRepository).getReferenceByTypeIsIn(types);
    }

    @Test
    void getReferenceByTypeIsIn_shouldReturnEmptyListForNonExistentTypes() {
        var nonExistentTypes = Collections.<RoleType>emptySet();

        when(roleRepository.getReferenceByTypeIsIn(nonExistentTypes))
                .thenReturn(Collections.emptyList());

        var result = roleService.getReferenceByTypeIsIn(nonExistentTypes);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(roleRepository).getReferenceByTypeIsIn(nonExistentTypes);
    }

    @Test
    void findAll_shouldReturnAllRoles() {
        var expectedRoles = List.of(
                createRole(1L, RoleType.ROLE_ADMIN),
                createRole(2L, RoleType.ROLE_USER)
        );

        when(roleRepository.findAll()).thenReturn(expectedRoles);

        var result = roleService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedRoles, result);

        verify(roleRepository).findAll();
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoRolesExist() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());

        var result = roleService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(roleRepository).findAll();
    }

    private Role createRole(Long id, RoleType type) {
        var role = new Role();
        role.setId(id);
        role.setType(type);
        return role;
    }
}
