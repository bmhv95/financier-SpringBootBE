package com.personal.project.rest.controller;

import com.personal.project.rest.EnvelopeAPI;
import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.EnvelopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnvelopeResource implements EnvelopeAPI {
    private final EnvelopeService envelopeService;

    @Override
    public ResponseEntity<EnvelopeDTO> createNewEnvelope(EnvelopeDTO envelopeDTO) {
        EnvelopeDTO newEnvelope = envelopeService.createNewEnvelope(envelopeDTO);
        return ResponseEntity.created(URI.create("/api/envelopes/" + newEnvelope.getEnvelopeID())).body(newEnvelope);
    }

    @Override
    public ResponseEntity<List<EnvelopeDTO>> getAllEnvelopes() {
        return ResponseEntity.ok(envelopeService.getAllEnvelopes());
    }

    @Override
    public ResponseEntity<EnvelopeDTO> getEnvelopeById(Long id) {
        return ResponseEntity.ok(envelopeService.getEnvelopeByID(id));
    }

    @Override
    public ResponseEntity<EnvelopeDTO> updateEnvelopeById(Long id, EnvelopeDTO envelopeDTO) {
        return ResponseEntity.ok(envelopeService.updateEnvelopeByID(id, envelopeDTO));
    }

    @Override
    public ResponseEntity<Void> deleteEnvelopeById(Long id) {
        envelopeService.deleteEnvelopeByID(id);
        return ResponseEntity.noContent().build();
    }
}
