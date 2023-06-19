package com.personal.project.service.mapper;

import com.personal.project.entity.Envelope;
import com.personal.project.service.DTO.EnvelopeDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EnvelopeMapper {

    @Mapping(target = "date", source = "updateDate")
    EnvelopeDTO envelopeToEnvelopeDTO(Envelope envelope);

    List<EnvelopeDTO> envelopeListToEnvelopeDTOList(List<Envelope> envelopes);

    @Mapping(target = "ID", ignore = true)
    void updateEnvelope(EnvelopeDTO envelopeDTO, @MappingTarget Envelope envelope);
}
