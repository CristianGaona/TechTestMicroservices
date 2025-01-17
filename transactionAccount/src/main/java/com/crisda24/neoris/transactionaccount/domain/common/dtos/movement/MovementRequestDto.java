package com.crisda24.neoris.transactionaccount.domain.common.dtos.movement;

import com.crisda24.neoris.transactionaccount.domain.enums.MovementType;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.utils.ValidEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class MovementRequestDto {

    @Min(1)
    private Long idMovement;

    @NotNull
    @ValidEnum(enumClass = MovementType.class, message = "invalid movement type: DEPOSITO o RETIRO")
    private String movementType;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal value;

    @NotNull
    @Size(max = 20)
    @Pattern(regexp = "\\d+", message = "the account number must contain only digits")
    private String accountNumber;


}
