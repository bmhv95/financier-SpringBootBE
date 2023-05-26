package com.personal.project.service.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class TransactionDTO {
    private Long transactionID;

    private BigDecimal transactionAmount;
    private String transactionName;
    private String transactionComment;

    private LocalDate transactionDate;
    private Long walletID;

    private String type;
}
