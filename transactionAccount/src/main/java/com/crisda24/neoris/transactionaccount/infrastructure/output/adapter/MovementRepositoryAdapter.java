package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.interfaces.MovementRangeDateDtoI;
import com.crisda24.neoris.transactionaccount.application.output.port.MovementRepository;
import com.crisda24.neoris.transactionaccount.domain.common.mappers.movement.MovementMapper;
import com.crisda24.neoris.transactionaccount.domain.enums.MovementType;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.MovementRepositoryJpa;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.MovementEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class MovementRepositoryAdapter implements MovementRepository {

    private final MovementRepositoryJpa movementRepositoryJpa;

    private final MovementMapper movementMapper;

    public MovementRepositoryAdapter(MovementRepositoryJpa movementRepositoryJpa, MovementMapper movementMapper) {
        this.movementRepositoryJpa = movementRepositoryJpa;
        this.movementMapper = movementMapper;
    }


    @Override
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(String startDate, String endDate, String accountNumber) {
        return movementRepositoryJpa.retrieveMovemenstByDates(startDate, endDate, accountNumber, MovementType.RETIRO.toString());
    }

    @Override
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(String startDate, String endDate, String accountNumber, Long clientId) {
        return movementRepositoryJpa.retrieveMovemenstByDates(startDate, endDate, accountNumber, clientId, MovementType.RETIRO.toString());
    }

    @Override
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(String startDate, String endDate, Long clientId) {
        return movementRepositoryJpa.retrieveMovemenstByDates(startDate, endDate, clientId, MovementType.RETIRO.toString());
    }

    @Override
    public void save(Movement movement) {
        MovementEntity movementEntity = movementMapper.toEntity(movement);
        movementRepositoryJpa.save(movementEntity);
    }

    @Override
    public BigDecimal findLastBalanceByAccountId(Long idAccount) {
        return movementRepositoryJpa.findLastBalanceByAccountId(idAccount);
    }
}
