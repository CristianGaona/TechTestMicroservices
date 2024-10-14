package com.example.clientmanagement.common.dto;

import com.example.clientmanagement.common.util.ValidEnum;
import com.example.clientmanagement.domain.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;

import lombok.Setter;


@Getter
@Setter
public class ClientRequestDto {

    @Min(1)
    private Long clientId;

    @NotBlank
    @Size(max = 100)
    @NotNull
    private String name;

    @NotNull
    @ValidEnum(enumClass = Gender.class, message= "Invalid gender: MALE o FEMALE")
    private String gender;

    @Min(18)
    @NotNull
    private Integer age;

    @NotBlank
    @Size(max = 255)
    @NotNull
    private String address;

    @NotBlank
    @Size(min =6, max = 25)
    @Pattern(regexp = "^[0-9]{6,25}", message = "Invalid format")
    @NotNull
    private String phoneNumber;

    @NotBlank
    @Size(max = 25)
    private String identification;

    @NotBlank
    @Size(min = 4, max = 100)
    @NotNull
    private String password;

    @NotNull
    private Boolean status;
}
