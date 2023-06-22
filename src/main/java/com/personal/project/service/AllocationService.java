package com.personal.project.service;

import com.personal.project.service.DTO.AllocationDTO;

import java.util.List;

public interface AllocationService {
    AllocationDTO createNewAllocation(String token, Long walletID, AllocationDTO allocationDTO);

    List<AllocationDTO> getAllAllocationsByToken(String token);

    List<AllocationDTO> getAllocationsByWalletID(String token, Long walletID);

    AllocationDTO getAllocationByID(String token, Long allocationID);

    AllocationDTO updateAllocationByID(String token, Long allocationID, AllocationDTO allocationDTO);

    void deleteAllocationByID(String token, Long allocationID);
}
