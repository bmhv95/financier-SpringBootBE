package com.personal.project.service.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EnvelopeTransactionDTO extends TransactionDTO {
    private Long envelopeID;
}
