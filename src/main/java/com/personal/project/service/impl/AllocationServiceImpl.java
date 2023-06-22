package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Allocation;
import com.personal.project.entity.Wallet;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.AllocationRepository;
import com.personal.project.repository.EnvelopeRepository;
import com.personal.project.service.AccountService;
import com.personal.project.service.AllocationService;
import com.personal.project.service.DTO.AllocationDTO;
import com.personal.project.service.WalletService;
import com.personal.project.service.facade.EnvelopeRepositoryFacade;
import com.personal.project.service.mapper.AllocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllocationServiceImpl implements AllocationService {
    private final AllocationRepository allocationRepository;
    private final AllocationMapper allocationMapper;
    private final AccountService accountService;
    private final WalletService walletService;
    private final EnvelopeRepositoryFacade envelopeRepositoryFacade;
    @Override
    public AllocationDTO createNewAllocation(String token, Long walletID, AllocationDTO allocationDTO) {
        Account account = accountService.getAccountEntityFromToken(token);

        Allocation allocation = Allocation.builder()
                .name(allocationDTO.getName())
                .amount(allocationDTO.getAmount())
                .wallet(walletService.getWalletEntityByID(token, walletID))
                .envelope(envelopeRepositoryFacade.getEnvelopeByID(allocationDTO.getEnvelopeID()))
                .build();

        return allocationMapper.toAllocationDTO(allocationRepository.save(allocation));
    }

    @Override
    public List<AllocationDTO> getAllAllocationsByToken(String token) {
        Long accountID = accountService.getAccountEntityFromToken(token).getID();
        return allocationMapper.toAllocationDTOs(allocationRepository.getAllocationsByAccountID(accountID));
    }

    @Override
    public List<AllocationDTO> getAllocationsByWalletID(String token, Long walletID){
        Wallet wallet = walletService.getWalletEntityByID(token, walletID);
        return allocationMapper.toAllocationDTOs(allocationRepository.findByWallet(wallet));
    }

    @Override
    public AllocationDTO getAllocationByID(String token, Long allocationID) {
        Allocation allocation = allocationRepository.findById(allocationID).orElseThrow(() -> ExceptionController.allocationNotFound(allocationID));
        checkOwnership(token, allocation);
        return allocationMapper.toAllocationDTO(allocation);
    }

    @Override
    public AllocationDTO updateAllocationByID(String token, Long allocationID, AllocationDTO allocationDTO) {
        Allocation allocation = allocationRepository.findById(allocationID).orElseThrow(() -> ExceptionController.allocationNotFound(allocationID));
        checkOwnership(token, allocation);
        allocationMapper.updateAllocation(allocationDTO, allocation);
        if(allocationDTO.getWalletID() != null){
            allocation.setWallet(walletService.getWalletEntityByID(token, allocationDTO.getWalletID()));
        }
        if(allocationDTO.getEnvelopeID() != null){
            allocation.setEnvelope(envelopeRepositoryFacade.getEnvelopeByID(allocationDTO.getEnvelopeID()));
        }
        return allocationMapper.toAllocationDTO(allocationRepository.saveAndFlush(allocation));
    }

    @Override
    public void deleteAllocationByID(String token, Long allocationID) {
        Allocation allocation = allocationRepository.findById(allocationID).orElseThrow(() -> ExceptionController.allocationNotFound(allocationID));
        checkOwnership(token, allocation);
        allocationRepository.delete(allocation);
    }

    private void checkOwnership(String token, Allocation allocation){
        Account account = accountService.getAccountEntityFromToken(token);
        if(!allocation.getWallet().getAccount().getID().equals(account.getID())){
            throw ExceptionController.forbidden();
        }
    }
}
