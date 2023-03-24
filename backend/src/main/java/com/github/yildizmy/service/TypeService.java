package com.github.yildizmy.service;

import com.github.yildizmy.model.Type;
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

    /**
     * Fetches a single type reference (entity) by the given id
     *
     * @param id
     * @return Type
     */
    public Type getReferenceById(long id) {
        return typeRepository.getReferenceById(id);
    }
}
