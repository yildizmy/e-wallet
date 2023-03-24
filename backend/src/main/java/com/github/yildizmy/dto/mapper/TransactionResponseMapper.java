package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.response.TransactionResponse;
import com.github.yildizmy.model.Transaction;
import org.mapstruct.Mapper;

/**
 * Mapper used for mapping TransactionResponse fields
 */
@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {

    Transaction toEntity(TransactionResponse dto);

    TransactionResponse toDto(Transaction entity);
}
