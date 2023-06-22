package com.personal.project.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvelopeWithTransactionsDTO {
    private EnvelopeDTO envelope;
    private List<TransactionDTO> transactions;
}
