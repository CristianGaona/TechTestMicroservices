package com.example.clientmanagement.infrastructure.controller;

import com.example.clientmanagement.application.output.port.ClientRepository;
import com.example.clientmanagement.infrastructure.output.adapter.ClientRepositoryAdapter;
import com.example.clientmanagement.infrastructure.output.adapter.repository.ClientRepositoryJpa;
import com.example.clientmanagement.infrastructure.output.adapter.repository.entity.ClientEntity;
import com.example.clientmanagement.mockData.ClientMock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.clientmanagement.domain.common.dto.ClientRequestDto;
import com.example.clientmanagement.domain.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Import(ClientRepositoryAdapter.class)
public class ClientControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    private Client client;


    @BeforeEach
    void setup() {
        clientRepository.deleteAll();
        client = ClientMock.createClientEntity();
        System.out.println("Client ->"+ client.getClientId());
        //ClientEntity clientEntity = new ClientEntity();
        //BeanUtils.copyProperties(client, clientEntity);
        clientRepository.save(client);

    }

    @Test
    void testCreateClient() throws Exception {
        ClientRequestDto clientRequest = ClientMock.createClientSuccess();
        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Client successfully created"));
    }

    @Test
    void testFindAllClients() throws Exception {

        mockMvc.perform(get("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cristian Gaona"));
    }

    @Test
    void testUpdateClientSuccessfully() throws Exception {

        ClientRequestDto clientRequestDto = ClientMock.UpdatedClientRequestDto();

        mockMvc.perform(put("/api/v1/clients/{id}", client.getClientId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Client successfully updated"));

        //Verificación de los datos actualizados
        Client updatedClient = clientRepository.findById(client.getClientId()).orElseThrow();
        assert(updatedClient.getName()).equals("John Updated");
        assert(updatedClient.getPhoneNumber()).equals("0987654321");
    }

    @Test
    void testUpdateClientNotFound() throws Exception {

        ClientRequestDto clientRequestDto = ClientMock.UpdatedClientRequestDto();

        mockMvc.perform(put("/api/v1/clients/{id}", 50L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Client not found"));
    }

    @Test
    void testDeleteFailPublisher() throws Exception {
                mockMvc.perform(delete("/api/v1/clients/{id}", client.getClientId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error has occurred. Please try again later."));

        // Verificar que el cliente no se haya sido eliminado de la base de datos
        boolean clientExists = clientRepository.existsById(client.getClientId());
        assert(clientExists);
    }
}
