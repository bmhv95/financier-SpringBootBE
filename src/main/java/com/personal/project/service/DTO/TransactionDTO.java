package com.personal.project.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TransactionDTO {
    private Long ID;

    @NotNull
    private BigDecimal amount;
    private String name;
    private String comment;

    private LocalDate date;
    @NotNull
    private Long walletID;

    private String type;
}
