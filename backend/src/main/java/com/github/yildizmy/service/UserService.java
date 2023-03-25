package com.github.yildizmy.service;

import com.github.yildizmy.model.User;
import com.github.yildizmy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service used for User related operations
 */
@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetches a single user reference (entity) by the given id
     *
     * @param id
     * @return User
     */
    public User getReferenceById(long id) {
        return userRepository.getReferenceById(id);
    }
}
