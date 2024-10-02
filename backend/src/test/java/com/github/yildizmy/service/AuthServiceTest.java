package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.SignupRequestMapper;
import com.github.yildizmy.dto.request.LoginRequest;
import com.github.yildizmy.dto.request.SignupRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.JwtResponse;
import com.github.yildizmy.exception.ElementAlreadyExistsException;
import com.github.yildizmy.model.User;
import com.github.yildizmy.repository.UserRepository;
import com.github.yildizmy.security.JwtUtils;
import com.github.yildizmy.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SignupRequestMapper signupRequestMapper;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private UserDetailsImpl userDetails;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("testuser", "password");
        userDetails = new UserDetailsImpl(1L, "testuser", "password", "Test", "User",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Test
    void login_shouldReturnJwtResponse() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("test.jwt.token");

        JwtResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("test.jwt.token", response.getToken());
        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("Test", response.getFirstName());
        assertEquals("User", response.getLastName());
        assertEquals(Collections.singletonList("ROLE_USER"), response.getRoles());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(authentication);
    }

    @Test
    void signup_shouldCreateNewUser() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest(1L, "New", "User", "newuser", "new@example.com", "password", Set.of("ROLE_USER"));
        User newUser = new User();
        newUser.setId(2L);
        newUser.setUsername("newuser");

        when(userRepository.existsByUsernameIgnoreCase("newuser")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("new@example.com")).thenReturn(false);
        when(signupRequestMapper.toEntity(signupRequest)).thenReturn(newUser);
        when(userRepository.save(newUser)).thenReturn(newUser);

        CommandResponse response = authService.signup(signupRequest);

        assertNotNull(response);
        assertEquals(2L, response.id());

        verify(userRepository).existsByUsernameIgnoreCase("newuser");
        verify(userRepository).existsByEmailIgnoreCase("new@example.com");
        verify(signupRequestMapper).toEntity(signupRequest);
        verify(userRepository).save(newUser);
    }

    @Test
    void signup_shouldThrowExceptionWhenUsernameExists() {
        SignupRequest signupRequest = new SignupRequest(1L, "Existing", "User", "existinguser", "existing@example.com", "password", Set.of("ROLE_USER"));
        when(userRepository.existsByUsernameIgnoreCase("existinguser")).thenReturn(true);

        assertThrows(ElementAlreadyExistsException.class, () -> authService.signup(signupRequest));
        verify(userRepository).existsByUsernameIgnoreCase("existinguser");
        verify(userRepository, never()).existsByEmailIgnoreCase(anyString());
        verify(signupRequestMapper, never()).toEntity(any());
        verify(userRepository, never()).save(any());
    }
}
