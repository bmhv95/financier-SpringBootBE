package com.personal.project.service.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class EnvelopeDTO {
    private Long ID;

    private String name;
    private String description;

    @NotNull
    @Min(value = 0)
    private BigDecimal budget;
    private BigDecimal spent;
    private BigDecimal allocated;

    private boolean isActive;
}
