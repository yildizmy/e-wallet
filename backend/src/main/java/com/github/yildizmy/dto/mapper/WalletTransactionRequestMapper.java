package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.TransactionRequest;
import com.github.yildizmy.dto.request.WalletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper used for mapping from WalletRequest to TransactionRequest fields
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletTransactionRequestMapper {

    @Mapping(target = "amount", source = "balance")
    @Mapping(target = "description", constant = "Initial balance")
    @Mapping(target = "fromWalletIban", source = "iban")
    @Mapping(target = "toWalletIban", source = "iban")
    @Mapping(target = "typeId", constant = "1L")
    TransactionRequest toTransactionDto(WalletRequest entity);

    WalletRequest toWalletDto(TransactionRequest dto);
}
