package com.github.yildizmy.dto.request;

import com.github.yildizmy.domain.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for Transaction request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private Long id;

    @NotNull(message = "{validation.field.amount.required}")
    private BigDecimal amount;

    @Size(max = 50, message = "{validation.field.description.length}")
    private String description;

    private Instant createdAt;

    private UUID referenceNumber;

    private Status status;

    @NotBlank(message = "{validation.iban.sender.required}")
    private String fromWalletIban;

    @NotBlank(message = "{validation.iban.receiver.required}")
    private String toWalletIban;

    @NotNull(message = "{validation.field.type.required}")
    private Long typeId;
}
