package com.github.yildizmy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet request
 */
@Data
public class WalletRequest {

    private Long id;

    @Size(min = 26, max = 26, message = "{iban.size}")
    @NotBlank(message = "{iban.notblank}")
    private String iban;

    @Size(min = 3, max = 50, message = "{name.size}")
    @NotBlank(message = "{name.notblank}")
    private String name;

    @NotNull(message = "{balance.notnull}")
    private BigDecimal balance;

    @NotNull(message = "{userId.notnull}")
    private Long userId;
}
