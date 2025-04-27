package com.github.yildizmy.service;

import com.github.yildizmy.domain.entity.User;
import com.github.yildizmy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getReferenceById_shouldReturnUserReference() {
        var userId = 1L;
        var expectedUser = new User();
        expectedUser.setId(userId);

        when(userRepository.getReferenceById(userId)).thenReturn(expectedUser);

        var result = userService.getReferenceById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());

        verify(userRepository).getReferenceById(userId);
    }

    @Test
    void getReferenceById_shouldThrowExceptionWhenUserNotFound() {
        var nonExistentUserId = 999L;

        when(userRepository.getReferenceById(nonExistentUserId))
                .thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> userService.getReferenceById(nonExistentUserId));

        verify(userRepository).getReferenceById(nonExistentUserId);
    }
}
