package com.github.yildizmy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-transaction"
    )
    @SequenceGenerator(
            name = "sequence-transaction",
            sequenceName = "sequence_transaction",
            allocationSize = 5
    )
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 50)
    private String description;

    @Column(nullable = false)
    private Instant date;

    @Column(length = 12, nullable = false, unique = true)
    private String referenceNumber;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet senderWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet receiverWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Type type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction transaction = (Transaction) o;
        return getId() != null &&
                Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
