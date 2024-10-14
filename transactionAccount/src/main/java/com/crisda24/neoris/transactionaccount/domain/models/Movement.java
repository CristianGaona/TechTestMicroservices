package com.crisda24.neoris.transactionaccount.domain.models;

import com.crisda24.neoris.transactionaccount.domain.enums.MovementType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movement")
public class Movement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovement;

    @Column(nullable = false)
    private Timestamp dateMovement;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal movement;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;
}
