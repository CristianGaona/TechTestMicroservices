package com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.interfaces.MovementRangeDateDtoI;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovementRepositoryJpa extends JpaRepository<MovementEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT TO_CHAR(m.date_movement, 'YYYY/MM/DD') AS date, ac.account_number AS accountNumber, ac.account_type   AS type, CASE WHEN m.movement_type = 'RETIRO' THEN (m.balance - m.movement) ELSE (m.balance + m.movement) END AS initBalance, m.movement AS value, m.balance AS balance, m.movement_type AS movement, ac.id_client AS clientId FROM movement m LEFT JOIN account AS ac ON ac.id_account = m.id_account WHERE m.date_movement::DATE >= TO_DATE(:startDate, 'YYYY/MM/DD') AND m.date_movement::DATE <= TO_DATE(:endDate, 'YYYY/MM/DD') AND ac.account_number =:accountNumber")
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("accountNumber") String accountNumber);

    @Query(nativeQuery = true, value = "SELECT TO_CHAR(m.date_movement, 'YYYY/MM/DD') AS date, ac.account_number AS accountNumber, ac.account_type   AS type, CASE WHEN m.movement_type = 'RETIRO' THEN (m.balance - m.movement) ELSE (m.balance + m.movement) END AS initBalance, m.movement AS value, m.balance AS balance, m.movement_type AS movement, ac.id_client AS clientId FROM movement m LEFT JOIN account AS ac ON ac.id_account = m.id_account WHERE m.date_movement::DATE >= TO_DATE(:startDate, 'YYYY/MM/DD') AND m.date_movement::DATE <= TO_DATE(:endDate, 'YYYY/MM/DD') AND ac.account_number =:accountNumber AND ac.id_client =:clientId")
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("accountNumber") String accountNumber, @Param("clientId") Long clientId);

    @Query(nativeQuery = true, value = "SELECT TO_CHAR(m.date_movement, 'YYYY/MM/DD') AS date, ac.account_number AS accountNumber, ac.account_type   AS type, CASE WHEN m.movement_type = 'RETIRO' THEN (m.balance - m.movement) ELSE (m.balance + m.movement) END AS initBalance, m.movement AS value, m.balance AS balance, m.movement_type AS movement, ac.id_client AS clientId FROM movement m LEFT JOIN account AS ac ON ac.id_account = m.id_account WHERE m.date_movement::DATE >= TO_DATE(:startDate, 'YYYY/MM/DD') AND m.date_movement::DATE <= TO_DATE(:endDate, 'YYYY/MM/DD') AND ac.id_client =:clientId")
    public List<MovementRangeDateDtoI> retrieveMovemenstByDates(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("clientId") Long clientId);
}
