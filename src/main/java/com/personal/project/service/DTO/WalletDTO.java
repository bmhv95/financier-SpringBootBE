package com.personal.project.service.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WalletDTO {
    private Long walletID;

    private String walletName;
    private BigDecimal walletBalance;
}
