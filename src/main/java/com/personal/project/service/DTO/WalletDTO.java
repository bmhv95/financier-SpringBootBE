package com.personal.project.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WalletDTO {
    private Long ID;

    private String name;
    @NotNull
    private BigDecimal balance;
    private LocalDate updatedDate;

    private boolean isActive;
}
