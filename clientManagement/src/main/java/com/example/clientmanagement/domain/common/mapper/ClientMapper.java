package com.example.clientmanagement.domain.common.mapper;

import com.example.clientmanagement.domain.common.dto.ClientRequestDto;
import com.example.clientmanagement.domain.common.dto.ClientRequestPatchDto;
import com.example.clientmanagement.domain.common.dto.interfaces.ClientListResponseDto;
import com.example.clientmanagement.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "clientId", expression = "java(dto.getClientId() != null ?  dto.getClientId(): null)")
    Client dtoToEntity(ClientRequestDto dto);

    @Mapping(target = "clientId", expression = "java(dto.getClientId() != null ?  dto.getClientId(): clientCheck.getClientId())")
    @Mapping(target = "name", expression = "java(dto.getName() != null ? dto.getName(): clientCheck.getName())")

    @Mapping(target = "gender", expression = "java(dto.getGender() != null ? dto.getGender(): clientCheck.getGender())")

    @Mapping(target = "age", expression = "java(dto.getAge() != null ? dto.getAge(): clientCheck.getAge())")

    @Mapping(target = "address", expression = "java(dto.getAddress() != null ? dto.getAddress(): clientCheck.getAddress())")

    @Mapping(target = "phoneNumber", expression = "java(dto.getPhoneNumber() != null ? dto.getPhoneNumber(): clientCheck.getPhoneNumber())")

    @Mapping(target = "identification", expression = "java(dto.getIdentification() != null ? dto.getIdentification(): clientCheck.getIdentification())")

    @Mapping(target = "password", expression = "java(dto.getPassword() != null ? dto.getPassword(): clientCheck.getPassword())")

    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? dto.getStatus(): clientCheck.getStatus())")
    Client entityToDto(Client clientCheck, ClientRequestPatchDto dto);

    List<ClientListResponseDto> clientsToClientListResponseDtos(List<Client> clients);


}
