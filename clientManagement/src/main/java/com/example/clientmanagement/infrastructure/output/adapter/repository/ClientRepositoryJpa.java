package com.example.clientmanagement.infrastructure.output.adapter.repository;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.infrastructure.output.adapter.repository.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepositoryJpa extends JpaRepository<ClientEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT c.client_id AS id, c.name AS name FROM client AS c WHERE c.client_id IN (:ids)")
    public List<ClientInfoResponseDtoI> findClientsInfoByIds(@Param("ids") List<Long> ids);

    public ClientEntity findByIdentification(String identification);

    public ClientEntity findByPhoneNumber(String phoneNumber);

    public ClientEntity findByClientId(Long clientId);


}
