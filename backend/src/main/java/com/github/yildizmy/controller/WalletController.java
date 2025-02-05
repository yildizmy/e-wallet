package com.github.yildizmy.controller;

import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.dto.request.WalletRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.WalletResponse;
import com.github.yildizmy.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    /**
     * Fetches a single wallet by the given id.
     *
     * @param id
     * @return WalletResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> findById(@PathVariable long id) {
        final WalletResponse response = walletService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches a single wallet by the given iban.
     *
     * @param iban
     * @return WalletResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping("/iban/{iban}")
    public ResponseEntity<WalletResponse> findByIban(@PathVariable String iban) {
        final WalletResponse response = walletService.findByIban(iban);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches a single wallet by the given userId.
     *
     * @param userId
     * @return WalletResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<WalletResponse>> findByUserId(@PathVariable long userId) {
        final List<WalletResponse> response = walletService.findByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches all wallets based on the given paging and sorting parameters.
     *
     * @param pageable
     * @return List of WalletResponse wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @GetMapping
    public ResponseEntity<Page<WalletResponse>> findAll(Pageable pageable) {
        final Page<WalletResponse> response = walletService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new wallet using the given request parameters.
     *
     * @param request
     * @return id of the created wallet wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @PostMapping
    public ResponseEntity<CommandResponse> create(@Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Transfer funds between wallets.
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @PostMapping("/transfer")
    public ResponseEntity<CommandResponse> transferFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.transferFunds(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Adds funds to the given wallet.
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @PostMapping("/addFunds")
    public ResponseEntity<CommandResponse> addFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.addFunds(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Withdraw funds from the given wallet.
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @PostMapping("/withdrawFunds")
    public ResponseEntity<CommandResponse> withdrawFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.withdrawFunds(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates wallet using the given request parameters.
     *
     * @param request
     * @return id of the updated wallet wrapped by ResponseEntity<T>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @PutMapping("/{id}")
    public ResponseEntity<CommandResponse> update(@PathVariable long id, @Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.update(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes wallet by the given id.
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.domain.enums.RoleType).ROLE_USER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        walletService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
