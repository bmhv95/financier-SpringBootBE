package com.personal.project.service.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountDTO {
    @JsonIgnore
    private Long ID;
    @NotNull
    private String accountName;

    private String firstName;
    private String lastName;
    @JsonIgnore
    @NotNull
    private String password;
    private String phoneNumber;
    @NotNull
    private String email;

    private LocalDate createDate;
    private LocalDate updateDate;
}
