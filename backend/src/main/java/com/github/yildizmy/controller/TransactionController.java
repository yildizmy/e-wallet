package com.github.yildizmy.controller;

import com.github.yildizmy.dto.response.TransactionResponse;
import com.github.yildizmy.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Fetches a single transaction by the given id.
     *
     * @param id
     * @return TransactionResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findById(@PathVariable long id) {
        final TransactionResponse response = transactionService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches a single transaction by the given referenceNumber.
     *
     * @param referenceNumber
     * @return TransactionResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping("/references/{referenceNumber}")
    public ResponseEntity<TransactionResponse> findByReferenceNumber(@PathVariable UUID referenceNumber) {
        final TransactionResponse response = transactionService.findByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches all transaction by the given userId.
     *
     * @param userId
     * @return List of TransactionResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<TransactionResponse>> findAllByUserId(@PathVariable long userId) {
        final Page<TransactionResponse> response = new PageImpl<>(transactionService.findAllByUserId(userId));
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches all transactions based on the given paging and sorting parameters.
     *
     * @param pageable
     * @return List of TransactionResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> findAll(Pageable pageable) {
        final Page<TransactionResponse> response = transactionService.findAll(pageable);
        return ResponseEntity.ok(response);
    }
}
