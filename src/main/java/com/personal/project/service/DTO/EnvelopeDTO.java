package com.personal.project.service.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EnvelopeDTO {
    private Long envelopeID;

    private String envelopeName;
    @NotNull
    @Min(value = 0)
    private BigDecimal envelopeBudgetAmount;
    private BigDecimal envelopeCurrentBalance;

    private LocalDate envelopeDate;
}
