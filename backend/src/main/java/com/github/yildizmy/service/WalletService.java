package com.github.yildizmy.service;

import com.github.yildizmy.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service used for Wallet related operations
 */
@Slf4j(topic = "WalletService")
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
}

