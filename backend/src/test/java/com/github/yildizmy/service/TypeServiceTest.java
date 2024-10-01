package com.github.yildizmy.service;

import com.github.yildizmy.model.Type;
import com.github.yildizmy.repository.TypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private TypeService typeService;

    @Test
    void getReferenceById_shouldReturnTypeReference() {
        long typeId = 1L;
        Type expectedType = new Type();
        expectedType.setId(typeId);
        when(typeRepository.getReferenceById(typeId)).thenReturn(expectedType);

        Type result = typeService.getReferenceById(typeId);

        assertNotNull(result);
        assertEquals(typeId, result.getId());
        verify(typeRepository, times(1)).getReferenceById(typeId);
    }

    @Test
    void getReferenceById_shouldThrowExceptionWhenTypeNotFound() {
        long nonExistentTypeId = 999L;
        when(typeRepository.getReferenceById(nonExistentTypeId))
                .thenThrow(new EntityNotFoundException("Type not found"));

        assertThrows(EntityNotFoundException.class, () -> {
            typeService.getReferenceById(nonExistentTypeId);
        });
        verify(typeRepository, times(1)).getReferenceById(nonExistentTypeId);
    }
}
