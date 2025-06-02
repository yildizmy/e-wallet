package com.github.yildizmy.service;

import com.github.yildizmy.domain.entity.User;
import com.github.yildizmy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service used for User related operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetches a single user reference (entity) by the given id.
     *
     * @param id
     * @return User
     */
    public User getReferenceById(long id) {
        return userRepository.getReferenceById(id);
    }


     /**
     * ❌ Example of SQL Injection via dynamic query string
     * DO NOT DO THIS IN REAL CODE!
     */
    public void searchUserByName(String name) {
        String query = "SELECT u FROM User u WHERE u.name = '" + name + "'";
        log.info("Running query: " + query); // ❌ Info leak
    }

    /**
     * ❌ Example of logging sensitive information
     */
    public void login(String username, String password) {
        // ❌ Logging sensitive data (credentials)
        log.info("User tried to login with username={} and password={}", username, password);

        log.info("✅ User {} logged in", username);
    }
}
