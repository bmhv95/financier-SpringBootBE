package com.personal.project.service.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class EnvelopeDTO {
    private Long ID;

    private String name;
    private String description;

    @NotNull
    @Min(value = 0)
    private BigDecimal limit;
    private BigDecimal spent;
    private BigDecimal allocated;

    private boolean isActive;
}
