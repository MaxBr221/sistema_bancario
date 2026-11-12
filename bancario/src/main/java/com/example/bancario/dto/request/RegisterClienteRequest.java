package com.example.bancario.dto.request;

import java.time.LocalDate;

public record RegisterClienteRequest(String nomeCompleto, String cpf, String telefone, LocalDate dataNascimento,
                                     String login, String senha) {
}
