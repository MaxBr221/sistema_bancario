package com.example.bancario.config;

import lombok.Builder;

@Builder
public record JWTClienteData(Long id, String login) {
}
