package com.personal.project.service.DTO;

import com.personal.project.entity.Period;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SpendingDTO extends EnvelopeDTO{
    private LocalDate date;

    private Period periodType;
    private Long periodCount;

    private String type = "SPENDING";
}
