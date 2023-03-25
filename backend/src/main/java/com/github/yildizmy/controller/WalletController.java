package com.github.yildizmy.controller;

import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.dto.request.WalletRequest;
import com.github.yildizmy.dto.response.ApiResponse;
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

import java.time.Clock;
import java.time.Instant;

import static com.github.yildizmy.common.Constants.SUCCESS;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final Clock clock;
    private final WalletService walletService;

    /**
     * Fetches a single wallet by the given id
     *
     * @param id
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WalletResponse>> findById(@PathVariable long id) {
        final WalletResponse response = walletService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches a single wallet by the given iban
     *
     * @param iban
     * @return WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @GetMapping("/ibans/{iban}")
    public ResponseEntity<ApiResponse<WalletResponse>> findByIban(@PathVariable String iban) {
        final WalletResponse response = walletService.findByIban(iban);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all wallets based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of WalletResponse wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<WalletResponse>>> findAll(Pageable pageable) {
        final Page<WalletResponse> response = walletService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new wallet using the given request parameters
     *
     * @param request
     * @return id of the created wallet wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Transfer funds between wallets
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PostMapping("/transferFunds")
    public ResponseEntity<ApiResponse<CommandResponse>> transferFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.transferFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Adds funds to the given wallet
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PostMapping("/addFunds")
    public ResponseEntity<ApiResponse<CommandResponse>> addFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.addFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Withdraw funds from the given wallet
     *
     * @param request
     * @return id of the created transaction wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PostMapping("/withdrawFunds")
    public ResponseEntity<ApiResponse<CommandResponse>> withdrawFunds(@Valid @RequestBody TransactionRequest request) {
        final CommandResponse response = walletService.withdrawFunds(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates wallet using the given request parameters
     *
     * @param request
     * @return id of the updated wallet wrapped by ResponseEntity<ApiResponse<T>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody WalletRequest request) {
        final CommandResponse response = walletService.update(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes wallet by the given id
     *
     * @param id
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id) {
        walletService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
