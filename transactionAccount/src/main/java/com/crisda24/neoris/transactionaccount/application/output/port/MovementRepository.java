package com.crisda24.neoris.transactionaccount.application.output.port;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.interfaces.MovementRangeDateDtoI;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MovementRepository {

    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(String startDate,  String endDate,  String accountNumber);

    public List<MovementRangeDateDtoI> retrieveMovemenstByDates( String startDate,  String endDate, String accountNumber, Long clientId);

    public List<MovementRangeDateDtoI> retrieveMovemenstByDates( String startDate,  String endDate,Long clientId);

    public void save(Movement movement);

    BigDecimal findLastBalanceByAccountId(Long idAccount);
}
