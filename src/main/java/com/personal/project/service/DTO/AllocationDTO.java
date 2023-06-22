package com.personal.project.service.DTO;

import com.personal.project.entity.Period;
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

    private Long walletID;
    @NotNull
    private Long envelopeID;

    private Period periodType;
    private Long periodCount;
}
