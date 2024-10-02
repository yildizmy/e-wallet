package com.github.yildizmy.service;

import com.github.yildizmy.model.Role;
import com.github.yildizmy.model.RoleType;
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

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void getReferenceByTypeIsIn_shouldReturnRolesForGivenTypes() {
        Set<RoleType> types = new HashSet<>(Arrays.asList(RoleType.ROLE_ADMIN, RoleType.ROLE_USER));
        List<Role> expectedRoles = Arrays.asList(
                createRole(1L, RoleType.ROLE_ADMIN),
                createRole(2L, RoleType.ROLE_USER)
        );
        when(roleRepository.getReferenceByTypeIsIn(types)).thenReturn(expectedRoles);

        List<Role> result = roleService.getReferenceByTypeIsIn(types);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(role -> role.getType() == RoleType.ROLE_ADMIN));
        assertTrue(result.stream().anyMatch(role -> role.getType() == RoleType.ROLE_USER));
        verify(roleRepository, times(1)).getReferenceByTypeIsIn(types);
    }

    private Role createRole(Long id, RoleType type) {
        Role role = new Role();
        role.setId(id);
        role.setType(type);
        return role;
    }
}
