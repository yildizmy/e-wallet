package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.response.WalletResponse;
import com.github.yildizmy.model.Wallet;
import org.mapstruct.Mapper;

/**
 * Mapper used for mapping WalletResponse fields
 */
@Mapper(componentModel = "spring")
public interface WalletResponseMapper {

    Wallet toEntity(WalletResponse dto);

    WalletResponse toDto(Wallet entity);
}
