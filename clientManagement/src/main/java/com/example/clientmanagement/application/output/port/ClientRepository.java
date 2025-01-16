package com.example.clientmanagement.application.output.port;

import com.example.clientmanagement.domain.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.domain.model.Client;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ClientRepository {
    public List<ClientInfoResponseDtoI> findClientsInfoByIds(@Param("ids") List<Long> ids);
    public Client findByIdentification(String identification);
    public Client findByPhoneNumber(String phoneNumber);
    public Client findByClientId(Long clientId);
    public Client save(Client client);
    public void delete(Client client);
    public Optional<Client> findById(Long id);
    public List<Client> findAll();
}
