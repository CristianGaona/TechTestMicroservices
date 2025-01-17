package com.crisda24.neoris.transactionaccount.domain.models;

import com.crisda24.neoris.transactionaccount.domain.enums.MovementType;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.AccountEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movement implements Serializable {

    private Long idMovement;

    private Timestamp dateMovement;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @Digits(integer = 10, fraction = 2)
    private BigDecimal balance;

    @Digits(integer = 10, fraction = 2)
    private BigDecimal movement;

    private Account account;
}
