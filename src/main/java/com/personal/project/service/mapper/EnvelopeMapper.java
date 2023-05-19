package com.personal.project.service.mapper;

import com.personal.project.entity.Envelope;
import com.personal.project.service.DTO.EnvelopeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnvelopeMapper {
    @Mapping(source = "account.accountID", target = "accountID")
    EnvelopeDTO envelopeToEnvelopeDTO(Envelope envelope);

    List<EnvelopeDTO> envelopeListToEnvelopeDTOList(List<Envelope> envelopes);
}
