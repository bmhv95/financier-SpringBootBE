package com.personal.project.rest;

import com.personal.project.service.DTO.EnvelopeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/envelopes")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface EnvelopeAPI {
    @PostMapping({"/new"})
    ResponseEntity<EnvelopeDTO> createNewEnvelope(@RequestBody EnvelopeDTO envelopeDTO);

    @GetMapping("/{id}")
    ResponseEntity<EnvelopeDTO> getEnvelopeById(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<EnvelopeDTO> updateEnvelopeById(@PathVariable Long id, @RequestBody EnvelopeDTO envelopeDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEnvelopeById(@PathVariable Long id);
}
