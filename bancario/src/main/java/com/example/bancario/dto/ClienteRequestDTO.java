package com.example.bancario.dto;

import com.example.bancario.model.StatusConta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClienteRequestDTO(Long id, String nomeCompleto, String cpf, String telefone,
                                LocalDate dataNascimento, StatusConta statusConta,
                                LocalDateTime criadoEm) {
}
