package com.example.clientmanagement.common.dto.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientListResponseDto {
    private Long clientId;
    private String name;
    private String address;
    private String phoneNumber;
    private String password;
    private Boolean status;
}
