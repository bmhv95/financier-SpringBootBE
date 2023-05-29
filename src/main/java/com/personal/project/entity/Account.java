package com.personal.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acc_id")
    private Long accountID;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String accountName;
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    private LocalDate createDate;
    @UpdateTimestamp
    private LocalDate updateDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<AccountRoleAssignment> roleAssignments = new ArrayList<>();

}
