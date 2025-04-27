package com.github.yildizmy.service;

import com.github.yildizmy.config.MessageSourceConfig;
import com.github.yildizmy.dto.mapper.TransactionRequestMapper;
import com.github.yildizmy.dto.mapper.TransactionResponseMapper;
import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.dto.response.TransactionResponse;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.domain.entity.Transaction;
import com.github.yildizmy.domain.entity.Wallet;
import com.github.yildizmy.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionRequestMapper transactionRequestMapper;

    @Mock
    private TransactionResponseMapper transactionResponseMapper;

    @Mock
    private MessageSourceConfig messageConfig;

    private Transaction testTransaction;
    private TransactionResponse testTransactionResponse;

    @BeforeEach
    void setUp() {
        testTransaction = new Transaction();
        testTransaction.setId(1L);
        testTransaction.setReferenceNumber(UUID.randomUUID());
        testTransaction.setAmount(BigDecimal.valueOf(100));

        testTransactionResponse = new TransactionResponse();
        testTransactionResponse.setId(1L);
        testTransactionResponse.setReferenceNumber(testTransaction.getReferenceNumber());
        testTransactionResponse.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    void findById_shouldReturnTransactionResponse() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));
        when(transactionResponseMapper.toTransactionResponse(testTransaction)).thenReturn(testTransactionResponse);

        var result = transactionService.findById(1L);

        assertNotNull(result);
        assertEquals(testTransactionResponse, result);

        verify(transactionRepository).findById(1L);
        verify(transactionResponseMapper).toTransactionResponse(testTransaction);
    }

    @Test
    void findById_shouldThrowExceptionWhenTransactionNotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> transactionService.findById(1L));

        verify(transactionRepository).findById(1L);
    }

    @Test
    void findByReferenceNumber_shouldReturnTransactionResponse() {
        var referenceNumber = testTransaction.getReferenceNumber();

        when(transactionRepository.findByReferenceNumber(referenceNumber)).thenReturn(Optional.of(testTransaction));
        when(transactionResponseMapper.toTransactionResponse(testTransaction)).thenReturn(testTransactionResponse);

        var result = transactionService.findByReferenceNumber(referenceNumber);

        assertNotNull(result);
        assertEquals(testTransactionResponse, result);

        verify(transactionRepository).findByReferenceNumber(referenceNumber);
        verify(transactionResponseMapper).toTransactionResponse(testTransaction);
    }

    @Test
    void findByReferenceNumber_shouldThrowExceptionWhenTransactionNotFound() {
        var referenceNumber = UUID.randomUUID();

        when(transactionRepository.findByReferenceNumber(referenceNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> transactionService.findByReferenceNumber(referenceNumber));

        verify(transactionRepository).findByReferenceNumber(referenceNumber);
    }

    @Test
    void findAllByUserId_shouldReturnListOfTransactionResponses() {
        var userId = 1L;
        var transactions = List.of(testTransaction, testTransaction);

        when(transactionRepository.findAllByUserId(userId)).thenReturn(transactions);
        when(transactionResponseMapper.toTransactionResponse(any(Transaction.class))).thenReturn(testTransactionResponse);

        var result = transactionService.findAllByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testTransactionResponse, result.get(0));
        assertEquals(testTransactionResponse, result.get(1));

        verify(transactionRepository).findAllByUserId(userId);
        verify(transactionResponseMapper, times(2)).toTransactionResponse(any(Transaction.class));
    }

    @Test
    void findAllByUserId_shouldThrowExceptionWhenNoTransactionsFound() {
        var userId = 1L;

        when(transactionRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementFoundException.class, () -> transactionService.findAllByUserId(userId));

        verify(transactionRepository).findAllByUserId(userId);
    }

    @Test
    void findAll_shouldReturnPageOfTransactionResponses() {
        var pageable = Pageable.unpaged();
        var transactionPage = new PageImpl<>(List.of(testTransaction));

        when(transactionRepository.findAll(pageable)).thenReturn(transactionPage);
        when(transactionResponseMapper.toTransactionResponse(testTransaction)).thenReturn(testTransactionResponse);

        var result = transactionService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(testTransactionResponse, result.getContent().get(0));

        verify(transactionRepository).findAll(pageable);
        verify(transactionResponseMapper).toTransactionResponse(testTransaction);
    }

    @Test
    void findAll_shouldThrowExceptionWhenNoTransactionsFound() {
        var pageable = Pageable.unpaged();

        when(transactionRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(NoSuchElementFoundException.class, () -> transactionService.findAll(pageable));

        verify(transactionRepository).findAll(pageable);
    }

    @Test
    void create_shouldCreateNewTransaction() {
        var request = new TransactionRequest();
        request.setAmount(BigDecimal.valueOf(100));

        var fromWallet = new Wallet();
        fromWallet.setIban("FROM123");

        var toWallet = new Wallet();
        toWallet.setIban("TO123");

        testTransaction.setFromWallet(fromWallet);
        testTransaction.setToWallet(toWallet);

        when(transactionRequestMapper.toTransaction(request)).thenReturn(testTransaction);
        when(transactionRepository.save(testTransaction)).thenReturn(testTransaction);

        var result = transactionService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.id());

        verify(transactionRequestMapper).toTransaction(request);
        verify(transactionRepository).save(testTransaction);
    }
}
