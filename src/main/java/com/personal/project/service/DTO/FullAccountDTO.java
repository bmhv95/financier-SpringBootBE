package com.personal.project.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullAccountDTO {
    private Long accountID;
    private String accountName;
    @NotNull
    private String password;
    private String phoneNumber;
    @NotNull
    private String email;
}
