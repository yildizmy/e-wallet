package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.WalletRequest;
import com.github.yildizmy.model.Wallet;
import com.github.yildizmy.service.UserService;
import org.mapstruct.*;

/**
 * Mapper used for mapping WalletRequest fields
 */
@Mapper(componentModel = "spring")
public interface WalletRequestMapper {

    Wallet toEntity(WalletRequest dto);

    @Mapping(target = "name", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getName()))")
    @Mapping(target = "iban", expression = "java(org.apache.commons.lang3.StringUtils.upperCase(dto.getIban()))")
    @Mapping(target = "user", ignore = true)
    Wallet toEntity(WalletRequest dto, @Context UserService userService);

    WalletRequest toDto(Wallet entity);

    @AfterMapping
    default void toEntity(@MappingTarget Wallet target, WalletRequest source, @Context UserService userService) {
        target.setUser(userService.getReferenceById(source.getUserId()));
    }
}
