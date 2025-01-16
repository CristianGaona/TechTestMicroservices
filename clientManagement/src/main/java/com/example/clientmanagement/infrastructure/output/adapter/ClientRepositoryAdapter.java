package com.example.clientmanagement.infrastructure.output.adapter;

import com.example.clientmanagement.application.output.port.ClientRepository;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.domain.model.Client;
import com.example.clientmanagement.infrastructure.output.adapter.repository.ClientRepositoryJpa;
import com.example.clientmanagement.infrastructure.output.adapter.repository.entity.ClientEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientRepositoryJpa clientRepositoryJpa;

    public ClientRepositoryAdapter(ClientRepositoryJpa clientRepositoryJpa) {
        this.clientRepositoryJpa = clientRepositoryJpa;
    }

    @Override
    public List<ClientInfoResponseDtoI> findClientsInfoByIds(List<Long> ids) {
        return clientRepositoryJpa.findClientsInfoByIds(ids);
    }

    @Override
    public Client findByIdentification(String identification) {
        Client client = new Client();
        BeanUtils.copyProperties(clientRepositoryJpa.findByIdentification(identification), client);
        return client;
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        Client client = new Client();
        BeanUtils.copyProperties(clientRepositoryJpa.findByPhoneNumber(phoneNumber), client);
        return client;
    }

    @Override
    public Client findByClientId(Long clientId) {
        Client client = new Client();
        BeanUtils.copyProperties( clientRepositoryJpa.findByClientId(clientId), client);
        return client;
    }

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = new ClientEntity();
        BeanUtils.copyProperties(client, clientEntity);
        clientRepositoryJpa.save(clientEntity);
        return client;
    }

    @Override
    public void delete(Client client) {
        ClientEntity clientEntity = new ClientEntity();
        BeanUtils.copyProperties(client, clientEntity);
        clientRepositoryJpa.delete(clientEntity);
    }

    @Override
    public Optional<Client> findById(Long id) {
        Optional<Client> client = Optional.empty();
        BeanUtils.copyProperties(clientRepositoryJpa.findById(id), client);
        return client;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        BeanUtils.copyProperties(clientRepositoryJpa.findAll(), clients);
        return  clients;
    }

}
