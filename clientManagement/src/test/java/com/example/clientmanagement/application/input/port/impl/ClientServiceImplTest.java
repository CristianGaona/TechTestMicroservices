package com.example.clientmanagement.application.input.port.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import com.example.clientmanagement.application.input.port.ClientService;
import com.example.clientmanagement.application.output.port.ClientRepository;
import com.example.clientmanagement.domain.common.dto.ClientRequestDto;
import com.example.clientmanagement.domain.common.mapper.ClientMapper;
import com.example.clientmanagement.infrastructure.input.adapter.exception.GeneralException;
import com.example.clientmanagement.domain.model.Client;
import com.example.clientmanagement.infrastructure.output.adapter.PublisherAdapter;
import com.example.clientmanagement.infrastructure.output.adapter.repository.ClientRepositoryJpa;
import com.example.clientmanagement.mockData.ClientMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ClientServiceImplTest {
    @MockBean
    private ClientMapper clientMapper;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @MockBean
    private PublisherAdapter publisherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClientWhenClientExistsThrowsException() {
        ClientRequestDto requestDto = ClientMock.createClientSuccess(); // Simula el DTO
        Client client = ClientMock.createClientEntity(); // Simula el cliente que ya existe

        when(clientMapper.dtoToEntity(requestDto)).thenReturn(client);
        when(clientRepository.findByIdentification(client.getIdentification())).thenReturn(client);

        // Verifica que se lanza la excepción correcta con el mensaje esperado
        GeneralException thrown = assertThrows(GeneralException.class, () -> clientService.create(requestDto));
        assertEquals("There is already a client registered with this identification: " + client.getIdentification(), thrown.getMessage());

        // Verifica que se llamaron los métodos esperados
        verify(clientMapper).dtoToEntity(isA(ClientRequestDto.class));
        verify(clientRepository).findByIdentification(eq(client.getIdentification()));

        // Verifica que no hay mas interacciones
        verifyNoMoreInteractions(clientMapper, clientRepository);

    }

    @Test
    void createClientSuccess() {
        ClientRequestDto requestDto = ClientMock.createClientSuccess(); // Simula el DTO
        Client client = ClientMock.createClientEntity(); // Simula creación del cliente

        when(clientMapper.dtoToEntity(requestDto)).thenReturn(client);

        // Verificar que no exista clientes con el mismo identificación y numero de telefono
        when(clientRepository.findByIdentification(requestDto.getIdentification())).thenReturn(null);
        when(clientRepository.findByPhoneNumber(requestDto.getPhoneNumber())).thenReturn(null);

        // Invoca al servicio para guaradar o crear el cliente
        clientService.create(requestDto);

        // Verificar que se llame al metodo save para guardar el registro del nuevo cliente
        verify(clientRepository).save(client);
    }


    @Test
    void disablesClientHasAccounts() {
        Client client = ClientMock.createClientEntity(); // Simula cliente existente
        when(clientRepository.findByClientId(1L)).thenReturn(client); // Verifica si el cliente esta creado

        // Simula una comunicación asíncrona, para verificar si el cliente esta asociado a una cuenta
        when(publisherService.sendAndReceiveAccountExists(1L)).thenReturn(true);

        //Como el cliente si esta asociado a una cuentas, en este caso solo se deshabilita
        clientService.delete(1L);

        // Compara si el usuario cambio de estado
        assertFalse(client.getStatus());

        // Verifica que se invoque o se llame al método save
        verify(clientRepository).save(client);
    }

    @Test
    void deleteWhenClientNoHasAccounts() {
        Client client = ClientMock.createClientEntity(); // Simula cliente existente

        when(clientRepository.findByClientId(1L)).thenReturn(client); // Verifica si el cliente esta creado

        // Simula una comunicación asíncrona, para verificar si el cliente esta asociado a una cuenta
        when(publisherService.sendAndReceiveAccountExists(1L)).thenReturn(false);

        // En este caso se elemina el cliente ya que no esta asociado a una cuenta y por lo tanto tampoco hay movimientos.
        clientService.delete(1L);

        // Verifica que se invoque o se llame al método save
        verify(clientRepository).delete(client);
    }
}
