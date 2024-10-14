package com.example.clientmanagement.common.dto;

import com.example.clientmanagement.domain.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestPatchDto {
    @Min(1)
    private Long clientId;

    @Size(max = 100)
    private String name;

    private Gender gender;

    @Min(18)
    private Integer age;

    @Size(max = 255)
    private String address;

    @Size(min = 6, max = 25)
    @Pattern(regexp = "^[0-9]{6,25}", message = "Invalid phone number format")
    private String phoneNumber;

    @Size(max = 25)
    private String identification;

    @Size(min = 6, max = 100)
    private String password;

    private Boolean status;
}
