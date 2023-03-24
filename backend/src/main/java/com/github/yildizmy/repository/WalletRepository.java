package com.github.yildizmy.repository;

import com.github.yildizmy.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByIban(String iban);

    boolean existsByUserIdAndIbanIgnoreCase(Long userId, String iban);

    boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);
}
