package com.github.yildizmy.service;

import com.github.yildizmy.domain.entity.Type;
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

    @InjectMocks
    private TypeService typeService;

    @Mock
    private TypeRepository typeRepository;

    @Test
    void getReferenceById_shouldReturnTypeReference() {
        var typeId = 1L;
        var expectedType = new Type();
        expectedType.setId(typeId);

        when(typeRepository.getReferenceById(typeId)).thenReturn(expectedType);

        var result = typeService.getReferenceById(typeId);

        assertNotNull(result);
        assertEquals(typeId, result.getId());

        verify(typeRepository).getReferenceById(typeId);
    }

    @Test
    void getReferenceById_shouldThrowExceptionWhenTypeNotFound() {
        var nonExistentTypeId = 999L;

        when(typeRepository.getReferenceById(nonExistentTypeId))
                .thenThrow(new EntityNotFoundException("Type not found"));

        assertThrows(EntityNotFoundException.class, () -> typeService.getReferenceById(nonExistentTypeId));

        verify(typeRepository).getReferenceById(nonExistentTypeId);
    }
}
