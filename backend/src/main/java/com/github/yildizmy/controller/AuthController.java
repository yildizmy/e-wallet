package com.github.yildizmy.controller;

import com.github.yildizmy.dto.request.LoginRequest;
import com.github.yildizmy.dto.request.SignupRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.JwtResponse;
import com.github.yildizmy.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates users by their credentials.
     *
     * @param request
     * @return JwtResponse
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        final JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Registers users using their credentials and user info.
     *
     * @param request
     * @return id of the registered user
     */
    @PostMapping("/signup")
    public ResponseEntity<CommandResponse> signup(@Valid @RequestBody SignupRequest request) {
        final CommandResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
