package com.example.clientmanagement.application.input.port;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientInfoResponseDtoI;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientListResponseDto;
import com.example.clientmanagement.domain.common.dto.ClientRequestDto;
import com.example.clientmanagement.domain.common.dto.ClientRequestPatchDto;
import com.example.clientmanagement.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public void create(ClientRequestDto clientRequestDto);
    public void delete(Long clientId);
    public Client update(Long id, ClientRequestDto client);
    public List<ClientListResponseDto> findAll();
    public List<ClientInfoResponseDtoI> findClientsInfoByIds(List<Long> ids);
    public Optional<Client> findById(Long id);
    public void findByIndentification(String indentification);
    public void findByPhoneNumber(String phoneNumber);
    public Client clientExists(Long id);
    public Client updatePatch(Long id, ClientRequestPatchDto updateFields);
}
