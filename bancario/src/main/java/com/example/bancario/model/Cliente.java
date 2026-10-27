package com.example.bancario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nomeCompleto;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private LocalDate dataNascimento;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusConta statusConta;
    @Column(nullable = false)
    private LocalDateTime criadoEm;



}
