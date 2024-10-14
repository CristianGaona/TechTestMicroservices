package com.example.clientmanagement.infrastructure.repository;
import com.example.clientmanagement.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.common.dto.interfaces.ClientListResponseDto;
import com.example.clientmanagement.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(nativeQuery = true, value = "SELECT c.client_id AS id, c.name AS name FROM client AS c WHERE c.client_id IN (:ids)")
    public List<ClientInfoResponseDtoI> findClientsInfoByIds(@Param("ids") List<Long> ids);

    public Client findByIdentification(String identification);

    public Client findByPhoneNumber(String phoneNumber);

    public Client findByClientId(Long clientId);

}
