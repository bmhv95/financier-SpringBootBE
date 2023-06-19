package com.personal.project.service.mapper;

import com.personal.project.entity.Allocation;
import com.personal.project.service.DTO.AllocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AllocationMapper {
    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "walletID", source = "wallet.ID")
    @Mapping(target = "envelopeID", source = "envelope.ID")
    AllocationDTO toAllocationDTO(Allocation allocation);

    List<AllocationDTO> toAllocationDTOs(List<Allocation> allocations);

    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "envelope", ignore = true)
    void updateAllocation(AllocationDTO allocationDTO, @MappingTarget Allocation allocation);
}
