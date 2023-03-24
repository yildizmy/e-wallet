package com.github.yildizmy.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"referenceNumber"})
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
    private Instant createdAt;

    @Column(nullable = false, unique = true)
    private UUID referenceNumber;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet fromWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet toWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Type type;
}
