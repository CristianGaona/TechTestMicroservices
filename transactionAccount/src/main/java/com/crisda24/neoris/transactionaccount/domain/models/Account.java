package com.crisda24.neoris.transactionaccount.domain.models;

import com.crisda24.neoris.transactionaccount.domain.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account implements Serializable {

    private Long idAccount;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Digits(integer = 10, fraction = 2)
    private BigDecimal initBalance;

    private Boolean status;

    private Long idClient;

    private List<Movement> movements = new ArrayList<>();
}

