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
    ResponseEntity<EnvelopeDTO> createNewEnvelope(@RequestHeader("Authorization") String token, @RequestBody EnvelopeDTO envelopeDTO);

    @GetMapping
    ResponseEntity<List<EnvelopeDTO>> getAllEnvelopesByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<EnvelopeDTO> getEnvelopeById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<EnvelopeDTO> updateEnvelopeById(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody EnvelopeDTO envelopeDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEnvelopeById(@RequestHeader("Authorization") String token, @PathVariable Long id);
}
