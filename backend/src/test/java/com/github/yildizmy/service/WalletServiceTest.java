package com.github.yildizmy.service;

import com.github.yildizmy.config.MessageSourceConfig;
import com.github.yildizmy.dto.mapper.WalletRequestMapper;
import com.github.yildizmy.dto.mapper.WalletResponseMapper;
import com.github.yildizmy.dto.mapper.WalletTransactionRequestMapper;
import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.dto.request.WalletRequest;
import com.github.yildizmy.dto.response.WalletResponse;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.domain.entity.Wallet;
import com.github.yildizmy.repository.WalletRepository;
import com.github.yildizmy.validator.IbanValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private WalletRequestMapper walletRequestMapper;

    @Mock
    private WalletResponseMapper walletResponseMapper;

    @Mock
    private WalletTransactionRequestMapper walletTransactionRequestMapper;

    @Mock
    private IbanValidator ibanValidator;

    @Mock
    private MessageSourceConfig messageConfig;

    @Test
    void findById_shouldReturnWalletResponse() {
        var wallet = createTestWallet(1L, "TEST123", "Test Wallet", BigDecimal.valueOf(1000));
        var expectedResponse = createTestWalletResponse(1L, "TEST123", "Test Wallet", BigDecimal.valueOf(1000));

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(walletResponseMapper.toWalletResponse(wallet)).thenReturn(expectedResponse);

        var result = walletService.findById(1L);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(walletRepository).findById(1L);
        verify(walletResponseMapper).toWalletResponse(wallet);
    }

    @Test
    void findById_shouldThrowExceptionWhenWalletNotFound() {
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> walletService.findById(1L));
        verify(walletRepository).findById(1L);
    }

    // Other tests...

    private Wallet createTestWallet(Long id, String iban, String name, BigDecimal balance) {
        var wallet = new Wallet();
        wallet.setId(id);
        wallet.setIban(iban);
        wallet.setName(name);
        wallet.setBalance(balance);
        return wallet;
    }

    private WalletResponse createTestWalletResponse(Long id, String iban, String name, BigDecimal balance) {
        var response = new WalletResponse();
        response.setId(id);
        response.setIban(iban);
        response.setName(name);
        response.setBalance(balance);
        return response;
    }

    private WalletRequest createTestWalletRequest(Long userId, String iban, String name, BigDecimal balance) {
        var request = new WalletRequest();
        request.setUserId(userId);
        request.setIban(iban);
        request.setName(name);
        request.setBalance(balance);
        return request;
    }

    private TransactionRequest createTestTransactionRequest(String fromWalletIban, String toWalletIban, BigDecimal amount) {
        var request = new TransactionRequest();
        request.setFromWalletIban(fromWalletIban);
        request.setToWalletIban(toWalletIban);
        request.setAmount(amount);
        return request;
    }
}
