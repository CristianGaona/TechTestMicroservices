package com.example.clientmanagement.infrastructure.output.adapter.repository.entity;

import com.example.clientmanagement.domain.model.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class ClientEntity extends PersonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;
}
