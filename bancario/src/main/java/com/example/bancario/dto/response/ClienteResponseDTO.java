package com.example.bancario.dto.response;

import com.example.bancario.model.Cliente;
import com.example.bancario.model.StatusConta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClienteResponseDTO(String nomeCompleto, String cpf, String telefone,
                                 LocalDate dataNascimento, StatusConta statusConta,
                                 LocalDateTime criadoEm) {
    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getNomeCompleto(), cliente.getCpf(), cliente.getTelefone(), cliente.getDataNascimento(), cliente.getStatusConta(), cliente.getCriadoEm());
    }
}
