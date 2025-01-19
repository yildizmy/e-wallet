package com.github.yildizmy.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yildizmy.config.MessageSourceConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.yildizmy.common.MessageKeys.ERROR_UNAUTHORIZED;
import static com.github.yildizmy.common.MessageKeys.ERROR_UNAUTHORIZED_DETAILS;

/**
 * Implements AuthenticationEntryPoint interface that is used for catching authentication errors.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final MessageSourceConfig messageConfig;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error(messageConfig.getMessage(ERROR_UNAUTHORIZED_DETAILS, authException.getMessage()));

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", messageConfig.getMessage(ERROR_UNAUTHORIZED));
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
