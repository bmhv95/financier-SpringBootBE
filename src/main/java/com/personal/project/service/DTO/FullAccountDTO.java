package com.personal.project.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullAccountDTO {
    private Long ID;

    @NotNull
    private String password;

    private String accountName;

    private String firstName;
    private String lastName;

    private String phoneNumber;

    @NotNull
    private String email;
}
