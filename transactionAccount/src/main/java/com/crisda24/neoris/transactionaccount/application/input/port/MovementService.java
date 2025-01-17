package com.crisda24.neoris.transactionaccount.application.input.port;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.MovementRangeDatesDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.MovementRequestDto;

import java.util.List;

public interface MovementService {

    public void create(MovementRequestDto movementRequestDto);
    public List<MovementRangeDatesDto> retrieveMovemenstByDates(String startDate, String endDate, String accountNumber, Long clientId);
}
