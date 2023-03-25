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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.yildizmy.common.Constants.*;

/**
 * Service used for Authentication related operations
 */
@Slf4j(topic = "AuthService")
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final SignupRequestMapper signupRequestMapper;

    /**
     * Authenticates users by their credentials
     *
     * @param request
     * @return JwtResponse
     */
    public JwtResponse login(LoginRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();

        log.info(LOGGED_IN_USER, new Object[]{request.getUsername()});
        return JwtResponse.builder().token(jwt).id(userDetails.getId()).username(userDetails.getUsername()).roles(roles).build();
    }

    /**
     * Registers a user by provided credentials and user info
     *
     * @param request
     * @return id of the registered user
     */
    public CommandResponse signup(SignupRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_NAME);
        if (userRepository.existsByEmailIgnoreCase(request.getEmail().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_EMAIL);

        final User user = signupRequestMapper.toEntity(request);
        userRepository.save(user);
        log.info(CREATED_USER, new Object[]{user.getUsername()});
        return CommandResponse.builder().id(user.getId()).build();
    }
}
