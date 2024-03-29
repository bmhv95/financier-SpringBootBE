package com.personal.project.service.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GoalDTO {
    private Long goalID;

    private String goalName;

    @NotNull
    @Min(value = 0)
    private BigDecimal goalAmount;
    private BigDecimal goalCurrentBalance;
    private LocalDate goalStartDate;
    private LocalDate goalEndDate;
}
