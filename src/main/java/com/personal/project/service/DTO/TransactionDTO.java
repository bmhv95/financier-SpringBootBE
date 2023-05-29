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
    private Long transactionID;

    @NotNull
    private BigDecimal transactionAmount;
    private String transactionName;
    private String transactionComment;

    private LocalDate transactionDate;
    @NotNull
    private Long walletID;

    private String type;
}
