package com.personal.project.service.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class IncomeDTO {
    private Long incomeID;

    private String incomeName;
    @NotNull
    @Min(value = 0)
    private BigDecimal incomeAmount;
    private boolean recurring;

    private Long walletID;
}
