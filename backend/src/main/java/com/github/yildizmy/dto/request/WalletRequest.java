package com.github.yildizmy.dto.request;

import com.github.yildizmy.validator.ValidIban;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletRequest {

    private Long id;

    @ValidIban(message = "{validation.iban.format}")
    @NotBlank(message = "{validation.iban.required}")
    private String iban;

    @Size(min = 3, max = 50, message = "{validation.field.name.length}")
    @NotBlank(message = "{validation.field.name.required}")
    private String name;

    @NotNull(message = "{validation.field.balance.required}")
    private BigDecimal balance;

    @NotNull(message = "{validation.field.user.required}")
    private Long userId;
}
