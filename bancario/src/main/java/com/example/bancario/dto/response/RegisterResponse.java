package com.example.bancario.dto.response;

import java.time.LocalDateTime;

public record RegisterResponse(String nomeCompleto, LocalDateTime criadoEm) {
}
