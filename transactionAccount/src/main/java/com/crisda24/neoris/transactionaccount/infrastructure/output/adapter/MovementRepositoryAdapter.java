package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.interfaces.MovementRangeDateDtoI;
import com.crisda24.neoris.transactionaccount.application.output.port.MovementRepository;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.MovementRepositoryJpa;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.MovementEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovementRepositoryAdapter implements MovementRepository {

    private final MovementRepositoryJpa movementRepositoryJpa;

    public MovementRepositoryAdapter(MovementRepositoryJpa movementRepositoryJpa) {
        this.movementRepositoryJpa = movementRepositoryJpa;
    }


    @Override
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(String startDate, String endDate, String accountNumber) {
        return movementRepositoryJpa.retrieveMovemenstByDates(startDate, endDate, accountNumber);
    }

    @Override
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(String startDate, String endDate, String accountNumber, Long clientId) {
        return movementRepositoryJpa.retrieveMovemenstByDates(startDate, endDate, accountNumber, clientId);
    }

    @Override
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(String startDate, String endDate, Long clientId) {
        return movementRepositoryJpa.retrieveMovemenstByDates(startDate, endDate, clientId);
    }

    @Override
    public void save(Movement movement){
        MovementEntity movementEntity = new MovementEntity();
        BeanUtils.copyProperties(movement, movementEntity);
        movementRepositoryJpa.save(movementEntity);
        BeanUtils.copyProperties(movementEntity, movement);
    }
}
