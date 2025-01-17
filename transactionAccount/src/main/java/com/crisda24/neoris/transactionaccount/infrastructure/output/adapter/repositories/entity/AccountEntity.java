package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity;

import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "account")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;

    @Column(length = 20, nullable = false, unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal initBalance;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private Long idClient;

    @JsonBackReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovementEntity> movements = new ArrayList<>();
}
