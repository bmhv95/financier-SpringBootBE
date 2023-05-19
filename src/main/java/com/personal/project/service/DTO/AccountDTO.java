package com.personal.project.service.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {
    private Long accountID;
    private String accountName;
    private String phoneNumber;
    private String email;
}
