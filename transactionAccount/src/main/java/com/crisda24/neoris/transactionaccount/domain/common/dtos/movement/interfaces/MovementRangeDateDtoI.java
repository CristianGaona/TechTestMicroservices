package com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.interfaces;

import java.math.BigDecimal;

public interface MovementRangeDateDtoI {


    String getDate();
    String getAccountNumber();
    String getType();
    BigDecimal getInitBalance();
    BigDecimal getValue();
    String getMovement();
    Long getClientId();
    BigDecimal getBalance();

}
