package com.personal.project.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AllocationDTO {
    private Long ID;

    private String name;
    @NotNull
    private BigDecimal amount;

    private LocalDate date;

    @NotNull
    private Long walletID;
    @NotNull
    private Long envelopeID;
}
