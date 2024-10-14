package com.example.clientmanagement.domain.model;

import com.example.clientmanagement.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Person implements Serializable {

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String address;

    @Column(length = 25)
    private String phoneNumber;

    @Column(length = 25, nullable = false)
    private String identification;

}
