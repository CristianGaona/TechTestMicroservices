package com.example.clientmanagement.infrastructure.controller;

import com.example.clientmanagement.application.input.port.ClientService;
import com.example.clientmanagement.domain.common.dto.ClientRequestDto;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientListResponseDto;
import com.example.clientmanagement.domain.model.Client;
import com.example.clientmanagement.infrastructure.input.adapter.rest.impl.ClientController;
import com.example.clientmanagement.mockData.ClientMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClientControllerTest {
    @Autowired
    private ClientController clientController;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientRequestDto clientRequestDto;
    private ClientRequestDto createBadRequestDto;
    private ClientListResponseDto clientListResponseDto;

    @BeforeEach
    void setup() {
        clientRequestDto = ClientMock.createClientSuccess();
        createBadRequestDto = ClientMock.createClientBadRequest();
        clientListResponseDto = ClientMock.createClientResponseDto();
    }

    @Test
    void createClientBadRequestTest() throws Exception {


        // Convertir el objeto en cadena JSON
        String content = (new ObjectMapper()).writeValueAsString(createBadRequestDto);

        // Contruir petición POST
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Simular petición
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(clientController)
                .build()
                .perform(requestBuilder);


        // Verificar respuesta
        actualPerformResult.andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"gender\":\"Invalid gender: MALE o FEMALE\",\"age\":\"must be greater than or equal to 18\"}"));
    }


    @Test
    void createClientSuccessTest() throws Exception {

        // Convertir el objeto en cadena JSON
        String content = (new ObjectMapper()).writeValueAsString(clientRequestDto);

        // Contruir petición POST
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Simular petición
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(clientController)
                .build()
                .perform(requestBuilder);

        // Verificar respuesta
        actualPerformResult.andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Client successfully created\"}"));
    }


    @Test
    void deleteClientSuccessfully() throws Exception {
        // Mock del servicio
        Mockito.doNothing().when(clientService).delete(1L);

        // Realizamos la petición DELETE
        ResultActions result = MockMvcBuilders.standaloneSetup(clientController)
                .build()
                .perform(delete("/api/v1/clients/{id}", 1L));

        // Verificamos el resultado
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Client successfully deleted"));
    }

    @Test
    void shouldUpdateClientSuccessfully() throws Exception {
        // Mock del servicio
        when(clientService.update(any(Long.class), any(ClientRequestDto.class))).thenReturn(new Client());

        // Realizamos la petición PUT
        ResultActions result =MockMvcBuilders.standaloneSetup(clientController)
                .build()
                .perform(put("/api/v1/clients/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientRequestDto)));

        // Verificamos el resultado
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Client successfully updated"));
    }

    @Test
    void shouldFindAllClientsSuccessfully() throws Exception {
        // Mock del servicio
        when(clientService.findAll()).thenReturn(Collections.singletonList(clientListResponseDto));

        // Realizamos la petición GET
        ResultActions result = MockMvcBuilders.standaloneSetup(clientController)
                .build()
                .perform(get("/api/v1/clients"));

        // Verificamos el resultado
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Cristian Daniel"));
    }

}
