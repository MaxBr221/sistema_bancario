package com.example.bancario.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "login é obrigatorio!") String login, @NotEmpty(message = "senha é obrigatorio!") String senha) {
}
