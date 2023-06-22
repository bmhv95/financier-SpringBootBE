package com.personal.project.service.DTO;

import com.personal.project.entity.Period;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class IncomeDTO {
    private Long ID;

    private String name;

    @NotNull
    @Min(value = 0)
    private BigDecimal amount;

    private LocalDate date;

    private Period periodType;
    private Long periodCount;

    private Long walletID;
}
