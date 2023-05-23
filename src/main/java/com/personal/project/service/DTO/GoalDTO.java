package com.personal.project.service.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GoalDTO {
    private Long goalID;

    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal goalCurrentBalance;
    private LocalDate goalStartDate;
    private LocalDate goalEndDate;
}
