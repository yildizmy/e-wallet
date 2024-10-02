package com.github.yildizmy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Wallet response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

    private Long id;
    private String iban;
    private String name;
    private BigDecimal balance;
    private UserResponse user;
}
