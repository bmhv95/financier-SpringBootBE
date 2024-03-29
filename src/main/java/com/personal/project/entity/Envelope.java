package com.personal.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envelope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envelope_id")
    private Long envelopeID;

    private String envelopeName;

    @Column(nullable = false)
    @Min(value = 0, message = "Envelope budget must not be negative")
    private BigDecimal envelopeBudgetAmount;
    @Column(nullable = false)
    private BigDecimal envelopeCurrentBalance;

    @CreationTimestamp
    private LocalDate envelopeDate;

    @UpdateTimestamp
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;

    @OneToMany(mappedBy = "envelope")
    private List<EnvelopeTransaction> envelopeTransaction;
}
