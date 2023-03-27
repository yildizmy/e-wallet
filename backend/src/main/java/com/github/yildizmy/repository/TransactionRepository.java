package com.github.yildizmy.repository;

import com.github.yildizmy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByReferenceNumber(UUID referenceNumber);

    @Query(value = "SELECT t " +
            "FROM Transaction t " +
            "LEFT JOIN Wallet w ON w.id IN (t.fromWallet.id, t.toWallet.id) " +
            "WHERE w.user.id = :userId " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findAllByUserId(@Param("userId") Long userId);
}
