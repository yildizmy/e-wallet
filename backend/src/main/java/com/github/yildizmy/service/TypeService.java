package com.github.yildizmy.service;

import com.github.yildizmy.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service used for Type related operations
 */
@Slf4j(topic = "TypeService")
@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;
}
