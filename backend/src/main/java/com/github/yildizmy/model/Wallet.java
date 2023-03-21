package com.github.yildizmy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Wallet {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-wallet"
    )
    @SequenceGenerator(
            name = "sequence-wallet",
            sequenceName = "sequence_wallet",
            allocationSize = 5
    )
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "senderWallet", cascade = CascadeType.ALL)
    private Set<Transaction> senderTransactions = new HashSet<>();

    public void addSenderTransaction(Transaction transaction) {
        senderTransactions.add(transaction);
        transaction.setSenderWallet(this);
    }

    public void removeSenderTransaction(Transaction transaction) {
        senderTransactions.remove(transaction);
        transaction.setSenderWallet(null);
    }

    @OneToMany(mappedBy = "receiverWallet", cascade = CascadeType.ALL)
    private Set<Transaction> receiverTransactions = new HashSet<>();

    public void addReceiverTransaction(Transaction transaction) {
        receiverTransactions.add(transaction);
        transaction.setReceiverWallet(this);
    }

    public void removeReceiverTransaction(Transaction transaction) {
        receiverTransactions.remove(transaction);
        transaction.setReceiverWallet(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wallet)) return false;
        Wallet wallet = (Wallet) o;
        return getId() != null &&
                Objects.equals(getId(), wallet.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
