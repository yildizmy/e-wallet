package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.WalletRequestMapper;
import com.github.yildizmy.dto.mapper.WalletResponseMapper;
import com.github.yildizmy.dto.mapper.WalletTransactionRequestMapper;
import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.dto.request.WalletRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.WalletResponse;
import com.github.yildizmy.exception.ElementAlreadyExistsException;
import com.github.yildizmy.exception.InsufficientFundsException;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.model.Wallet;
import com.github.yildizmy.repository.WalletRepository;
import com.github.yildizmy.validator.IbanValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.yildizmy.common.Constants.*;

/**
 * Service used for Wallet related operations
 */
@Slf4j(topic = "WalletService")
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;
    private final WalletRequestMapper walletRequestMapper;
    private final WalletResponseMapper walletResponseMapper;
    private final WalletTransactionRequestMapper walletTransactionRequestMapper;
    private final IbanValidator ibanValidator;

    /**
     * Fetches a single wallet by the given id
     *
     * @param id
     * @return WalletResponse
     */
    @Transactional(readOnly = true)
    public WalletResponse findById(long id) {
        return walletRepository.findById(id)
                .map(walletResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
    }

    /**
     * Fetches a single wallet by the given iban
     *
     * @param iban
     * @return WalletResponse
     */
    @Transactional(readOnly = true)
    public WalletResponse findByIban(String iban) {
        return walletRepository.findByIban(iban)
                .map(walletResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
    }

    /**
     * Fetches a single wallet by the given userId
     *
     * @param userId
     * @return WalletResponse
     */
    @Transactional(readOnly = true)
    public List<WalletResponse> findByUserId(long userId) {
        return walletRepository.findByUserId(userId).stream()
                .map(walletResponseMapper::toDto)
                .toList();
    }

    /**
     * Fetches a single wallet reference (entity) by the given id
     *
     * @param iban
     * @return Wallet
     */
    public Wallet getByIban(String iban) {
        return walletRepository.findByIban(iban)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
    }

    /**
     * Fetches all wallets based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of WalletResponse
     */
    @Transactional(readOnly = true)
    public Page<WalletResponse> findAll(Pageable pageable) {
        final Page<WalletResponse> wallets = walletRepository.findAll(pageable)
                .map(walletResponseMapper::toDto);
        if (wallets.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return wallets;
    }

    /**
     * Creates a new wallet using the given request parameters
     *
     * @param request
     * @return id of the created wallet
     */
    @Transactional
    public CommandResponse create(WalletRequest request) {
        if (walletRepository.existsByIbanIgnoreCase(request.getIban()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_IBAN);
        if (walletRepository.existsByUserIdAndNameIgnoreCase(request.getUserId(), request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_NAME);

        ibanValidator.isValid(request.getIban(), null);

        final Wallet wallet = walletRequestMapper.toEntity(request);
        walletRepository.save(wallet);
        log.info(CREATED_WALLET, new Object[]{wallet.getIban(), wallet.getName(), wallet.getBalance()});

        // add this initial amount to the transactions
        transactionService.create(walletTransactionRequestMapper.toTransactionDto(request));

        return CommandResponse.builder().id(wallet.getId()).build();
    }

    /**
     * Transfer funds between wallets
     *
     * @param request
     * @return id of the transaction
     */
    @Transactional
    public CommandResponse transferFunds(TransactionRequest request) {
        final Wallet toWallet = getByIban(request.getToWalletIban());
        final Wallet fromWallet = getByIban(request.getFromWalletIban());

        // check if the balance of sender wallet has equal or higher to/than transfer amount
        if (fromWallet.getBalance().compareTo(request.getAmount()) < 0)
            throw new InsufficientFundsException(FUNDS_CANNOT_BELOW_ZERO);

        // update balance of the sender wallet
        fromWallet.setBalance(fromWallet.getBalance().subtract(request.getAmount()));

        // update balance of the receiver wallet
        toWallet.setBalance(toWallet.getBalance().add(request.getAmount()));

        walletRepository.save(toWallet);
        log.info(UPDATED_WALLET_BALANCES, new Object[]{fromWallet.getBalance(), toWallet.getBalance()});

        final CommandResponse response = transactionService.create(request);
        return CommandResponse.builder().id(response.id()).build();
    }

    /**
     * Adds funds to the given wallet
     *
     * @param request
     * @return id of the transaction
     */
    @Transactional
    public CommandResponse addFunds(TransactionRequest request) {
        final Wallet toWallet = getByIban(request.getToWalletIban());

        // update balance of the receiver wallet
        toWallet.setBalance(toWallet.getBalance().add(request.getAmount()));

        walletRepository.save(toWallet);
        log.info(UPDATED_WALLET_BALANCE, new Object[]{toWallet.getBalance()});

        final CommandResponse response = transactionService.create(request);
        return CommandResponse.builder().id(response.id()).build();
    }

    /**
     * Withdraw funds from the given wallet
     *
     * @param request
     * @return id of the transaction
     */
    @Transactional
    public CommandResponse withdrawFunds(TransactionRequest request) {
        final Wallet fromWallet = getByIban(request.getFromWalletIban());

        // check if the balance of sender wallet has equal or higher to/than transfer amount
        if (fromWallet.getBalance().compareTo(request.getAmount()) < 0)
            throw new InsufficientFundsException(FUNDS_CANNOT_BELOW_ZERO);

        // update balance of the sender wallet
        fromWallet.setBalance(fromWallet.getBalance().subtract(request.getAmount()));

        walletRepository.save(fromWallet);
        log.info(UPDATED_WALLET_BALANCE, new Object[]{fromWallet.getBalance()});

        final CommandResponse response = transactionService.create(request);
        return CommandResponse.builder().id(response.id()).build();
    }

    /**
     * Updates wallet using the given request parameters
     *
     * @param request
     * @return id of the updated wallet
     */
    public CommandResponse update(long id, WalletRequest request) {
        final Wallet foundWallet = walletRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));

        // check if the iban is changed and new iban is already exists
        if (!request.getIban().equalsIgnoreCase(foundWallet.getIban()) &&
                walletRepository.existsByIbanIgnoreCase(request.getIban()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_IBAN);

        // check if the name is changed and new name is already exists in user's wallets
        if (!request.getName().equalsIgnoreCase(foundWallet.getName()) &&
                walletRepository.existsByUserIdAndNameIgnoreCase(request.getUserId(), request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_WALLET_NAME);

        ibanValidator.isValid(request.getIban(), null);

        final Wallet wallet = walletRequestMapper.toEntity(request);
        walletRepository.save(wallet);
        log.info(UPDATED_WALLET, new Object[]{wallet.getIban(), wallet.getName(), wallet.getBalance()});
        return CommandResponse.builder().id(wallet.getId()).build();
    }

    /**
     * Deletes wallet by the given id
     *
     * @param id
     */
    public void deleteById(long id) {
        final Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_WALLET));
        walletRepository.delete(wallet);
        log.info(DELETED_WALLET, new Object[]{wallet.getIban(), wallet.getName(), wallet.getBalance()});
    }
}
