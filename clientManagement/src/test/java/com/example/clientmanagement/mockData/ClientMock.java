package com.example.clientmanagement.mockData;

import com.example.clientmanagement.domain.common.dto.ClientRequestDto;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientListResponseDto;
import com.example.clientmanagement.domain.enums.Gender;
import com.example.clientmanagement.domain.model.Client;

public class ClientMock {
    public static ClientRequestDto createClientSuccess(){
        ClientRequestDto clientRequestDto;
        clientRequestDto = new ClientRequestDto();
        clientRequestDto.setAddress("Calle 10 de Agosto");
        clientRequestDto.setAge(21);
        clientRequestDto.setClientId(1L);
        clientRequestDto.setGender("FEMALE");
        clientRequestDto.setIdentification("110601503");
        clientRequestDto.setName("Maria Osorio");
        clientRequestDto.setPassword("1234");
        clientRequestDto.setPhoneNumber("098560234");
        clientRequestDto.setStatus(true);
        return clientRequestDto;
    }

    public static ClientRequestDto createClientBadRequest(){
        ClientRequestDto clientRequestDto;
        clientRequestDto = new ClientRequestDto();
        clientRequestDto.setAddress("Calle 10 de Agosto");
        clientRequestDto.setAge(17);
        clientRequestDto.setClientId(1L);
        clientRequestDto.setGender("MUJER");
        clientRequestDto.setIdentification("110601503");
        clientRequestDto.setName("Maria Osorio");
        clientRequestDto.setPassword("1234");
        clientRequestDto.setPhoneNumber("098560234");
        clientRequestDto.setStatus(true);
        return clientRequestDto;
    }

    public static Client createClientEntity(){
        Client client = new Client();
        client.setName("Cristian Gaona");
        client.setIdentification("11060613458");
        client.setStatus(true);
        client.setAge(29);
        client.setPassword("1234");
        client.setPhoneNumber("0985880290");
        client.setAddress("Calle siempre viva");
        client.setGender(Gender.MALE);
        return client;
    }

    public static ClientListResponseDto createClientResponseDto(){
        ClientListResponseDto client = new ClientListResponseDto();
        client.setClientId(1L);
        client.setAddress("Loja");
        client.setName("Cristian Daniel");
        client.setPhoneNumber("0985880290");
        client.setStatus(true);
        client.setPassword("1234");
        return client;

    }

    public static ClientRequestDto UpdatedClientRequestDto(){
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setName("John Updated");
        clientRequestDto.setPhoneNumber("0987654321");
        clientRequestDto.setIdentification("1106015602");
        clientRequestDto.setAge(30);
        clientRequestDto.setStatus(true);
        clientRequestDto.setPassword("newpassword123");
        clientRequestDto.setAddress("Updated Address");
        clientRequestDto.setGender("MALE");
        return clientRequestDto;
    }
}
