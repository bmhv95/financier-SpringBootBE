package com.personal.project.service.mapper;

import com.personal.project.entity.Wallet;
import com.personal.project.service.DTO.WalletDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {

    WalletDTO walletToWalletDTO(Wallet wallet);

    List<WalletDTO> walletListToWalletDTOList(List<Wallet> wallets);

    @Mapping(target = "walletID", ignore = true)
    void updateWallet(WalletDTO walletDTO, @MappingTarget Wallet wallet);
}
