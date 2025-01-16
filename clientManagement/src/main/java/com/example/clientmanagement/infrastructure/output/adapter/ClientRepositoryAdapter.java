package com.example.clientmanagement.infrastructure.output.adapter;

import com.example.clientmanagement.application.output.port.ClientRepository;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.domain.model.Client;
import com.example.clientmanagement.infrastructure.output.adapter.repository.ClientRepositoryJpa;
import com.example.clientmanagement.infrastructure.output.adapter.repository.entity.ClientEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ClientEntity clientEntity = clientRepositoryJpa.findByIdentification(identification);
        if(ObjectUtils.isEmpty(clientEntity)){
            return null;
        }
        Client client = new Client();
        BeanUtils.copyProperties(clientEntity, client);
        return client;
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        ClientEntity clientEntity = clientRepositoryJpa.findByPhoneNumber(phoneNumber);
        if(ObjectUtils.isEmpty(clientEntity)){
            return null;
        }
        Client client = new Client();
        BeanUtils.copyProperties(clientEntity, client);
        return client;
    }

    @Override
    public Client findByClientId(Long clientId) {
        ClientEntity clientEntity = clientRepositoryJpa.findByClientId(clientId);
        if(ObjectUtils.isEmpty(clientEntity)){
            return null;
        }
        Client client = new Client();
        BeanUtils.copyProperties( clientEntity, client);
        return client;
    }

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = new ClientEntity();
        BeanUtils.copyProperties(client, clientEntity);
        clientRepositoryJpa.save(clientEntity);
        BeanUtils.copyProperties(clientEntity, client);
        //System.out.println("CLIENTE-SERVICIO" + client.getClientId());
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
        return clientRepositoryJpa.findById(id)
            .map(entity -> {
                Client client = new Client();
                BeanUtils.copyProperties(entity, client);
                return client;
            });
    }

    @Override
    public List<Client> findAll() {
        List<ClientEntity> clientEntities = clientRepositoryJpa.findAll();
        if(ObjectUtils.isEmpty(clientEntities)){
            return Collections.emptyList();
        }

        return clientEntities.stream()
                .map(entity -> {
                    Client client = new Client();
                    BeanUtils.copyProperties(entity, client);
                    return client;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        clientRepositoryJpa.deleteAll();
    }

    @Override
    public Boolean existsById(Long id) {
        return clientRepositoryJpa.existsById(id);
    }

}
