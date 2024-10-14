package com.crisda24.neoris.transactionaccount.mockData;

import com.crisda24.neoris.transactionaccount.domain.enums.MovementType;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MovementMock {
    public static Movement createMovementEntity(){
        Movement movement = new Movement();
        movement.setDateMovement(new Timestamp(System.currentTimeMillis()));
        movement.setBalance(new BigDecimal("100.00"));
        movement.setMovement(new BigDecimal("100.00"));
        movement.setMovementType(MovementType.DEPOSITO);
        movement.setAccount(AccountMock.createAccountEntity());
        return movement;
    }
}
