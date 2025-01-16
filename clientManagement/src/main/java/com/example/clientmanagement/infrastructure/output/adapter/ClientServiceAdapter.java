package com.example.clientmanagement.infrastructure.output.adapter;

import com.example.clientmanagement.application.output.port.ClientRepository;
import com.example.clientmanagement.application.input.port.ClientService;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientListResponseDto;
import com.example.clientmanagement.domain.common.dto.ClientRequestDto;
import com.example.clientmanagement.domain.common.dto.ClientRequestPatchDto;
import com.example.clientmanagement.domain.common.mapper.ClientMapper;
import com.example.clientmanagement.infrastructure.input.adapter.rest.impl.ClientController;
import com.example.clientmanagement.infrastructure.input.adapter.exception.GeneralException;
import com.example.clientmanagement.domain.model.Client;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientServiceAdapter implements ClientService {

    private final ClientRepository clientRepository;
    private final PublisherAdapter publisherService;
    private final ClientMapper clientMapper;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    public ClientServiceAdapter(ClientRepository clientRepository, PublisherAdapter publisherService, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.publisherService = publisherService;
        this.clientMapper = clientMapper;
    }

    @Transactional
    @Override
    public void create(@Valid ClientRequestDto clientRequestDTO) {
        Client client = clientMapper.dtoToEntity(clientRequestDTO);
        this.findByIndentification(client.getIdentification().trim());
        this.findByPhoneNumber(client.getPhoneNumber().trim());
        this.clientRepository.save(client);
    }

    @Transactional
    @Override
    public void delete(Long clientId)  {
        Client client = this.clientExists(clientId);
        Boolean accountsByClient = publisherService.sendAndReceiveAccountExists(clientId);
        if(Objects.isNull(accountsByClient)){
            logger.error("accountsByClient is null");
            throw new GeneralException("An unexpected error has occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (accountsByClient) {
            client.setStatus(false);
            clientRepository.save(client);
        }else{
            clientRepository.delete(client);
        }
    }

    @Transactional
    @Override
    public Client update(Long id, ClientRequestDto clientUpdate) {
        Client clientExists = this.clientExists(id);
        clientUpdate.setClientId(clientExists.getClientId());
        Client client = clientMapper.dtoToEntity(clientUpdate);
        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public Client updatePatch(Long id, ClientRequestPatchDto clientRequestPatchDto ) {
        Client clientExists = this.clientExists(id);
        clientRequestPatchDto.setClientId(clientExists.getClientId());
        Client client = clientMapper.entityToDto(clientExists, clientRequestPatchDto);
        return clientRepository.save(client);
    }

    @Override
    public List<ClientListResponseDto> findAll() {
        List<Client> clients = this.clientRepository.findAll();
        return clientMapper.clientsToClientListResponseDtos(clients);
    }

    @Override
    public List<ClientInfoResponseDtoI> findClientsInfoByIds(List<Long> ids) {
        return clientRepository.findClientsInfoByIds(ids);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void findByIndentification(String identification) {
        Client client = clientRepository.findByIdentification(identification);
        if(Objects.nonNull(client)){
            throw new GeneralException("There is already a client registered with this identification: "+ identification, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void findByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findByPhoneNumber(phoneNumber);
        if(Objects.nonNull(client)){
            throw new GeneralException("There is already a client registered with this phone number: "+ phoneNumber, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Client clientExists(Long id) {
        Client clientCheck = clientRepository.findByClientId(id);
        if(Objects.isNull(clientCheck)){
            throw new GeneralException("Client not found", HttpStatus.BAD_REQUEST);
        }
        return clientCheck;
    }
}
