package com.personal.project.service.impl;

import com.personal.project.entity.Envelope;
import com.personal.project.repository.EnvelopeRepository;
import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.EnvelopeService;
import com.personal.project.service.mapper.EnvelopeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnvelopeServiceImpl implements EnvelopeService {
    private final EnvelopeMapper envelopeMapper;
    private final EnvelopeRepository envelopeRepository;
    @Override
    public EnvelopeDTO createNewEnvelope(EnvelopeDTO envelopeDTO) {
        checkDTO(envelopeDTO);
        Envelope newEnvelope = Envelope.builder()
                .envelopeName(envelopeDTO.getEnvelopeName())
                .envelopeBudgetAmount(envelopeDTO.getEnvelopeBudgetAmount())
                .envelopeCurrentBalance(envelopeDTO.getEnvelopeBudgetAmount())
                .build();

        return envelopeMapper.envelopeToEnvelopeDTO(envelopeRepository.save(newEnvelope));
    }
    @Override
    public List<EnvelopeDTO> getAllEnvelopes() {
        return envelopeMapper.envelopeListToEnvelopeDTOList(envelopeRepository.findAll());
    }

    @Override
    public EnvelopeDTO getEnvelopeByID(Long envelopeID) {
        return envelopeMapper.envelopeToEnvelopeDTO(envelopeRepository.findById(envelopeID).orElseThrow(() -> new RuntimeException("Envelope not found")));
    }

    @Override
    public EnvelopeDTO updateEnvelopeByID(Long envelopeID, EnvelopeDTO envelopeDTO) {
        checkDTO(envelopeDTO);

        Envelope envelope = envelopeRepository.findById(envelopeID).orElseThrow(() -> new RuntimeException("Envelope not found"));
        envelope.setEnvelopeName(envelopeDTO.getEnvelopeName());
        envelope.setEnvelopeBudgetAmount(envelopeDTO.getEnvelopeBudgetAmount());
        envelope.setEnvelopeCurrentBalance(envelopeDTO.getEnvelopeCurrentBalance());
        envelope.setEnvelopeDate(envelopeDTO.getEnvelopeDate());

        return envelopeMapper.envelopeToEnvelopeDTO(envelopeRepository.save(envelope));

    }

    @Override
    public void deleteEnvelopeByID(Long envelopeID) {
        Envelope envelope = envelopeRepository.findById(envelopeID).orElseThrow(() -> new RuntimeException("Envelope not found"));
        envelopeRepository.delete(envelope);
    }

    private void checkDTO(EnvelopeDTO envelopeDTO) {
        if (envelopeDTO == null) {
            throw new RuntimeException("EnvelopeDTO is null");
        }
    }
}
