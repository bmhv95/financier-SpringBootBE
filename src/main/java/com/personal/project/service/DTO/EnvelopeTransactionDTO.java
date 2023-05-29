package com.personal.project.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EnvelopeTransactionDTO extends TransactionDTO {
    @NotNull
    private Long envelopeID;
}
