package com.crisda24.neoris.transactionaccount.domain.common.dtos.movement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MovementRangeDatesDto {
    private BigDecimal value;
    private String type;
    private String date;
    private String accountNumber;
    private BigDecimal initBalance;
    private String movement;
    private BigDecimal balance;
    private String clientName;

    public MovementRangeDatesDto() {

    }
}
