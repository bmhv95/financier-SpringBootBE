package com.personal.project.service;

import com.personal.project.service.DTO.EnvelopeDTO;

import java.util.List;

public interface EnvelopeService {

    EnvelopeDTO createNewEnvelope(String token, EnvelopeDTO envelopeDTO);

    List<EnvelopeDTO> getAllEnvelopesByToken(String token);

    EnvelopeDTO getEnvelopeByID(Long envelopeID);

    EnvelopeDTO updateEnvelopeByID(Long envelopeID, EnvelopeDTO envelopeDTO);

    void deleteEnvelopeByID(Long envelopeID);
}
