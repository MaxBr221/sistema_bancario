package com.example.bancario.dto.response;

import com.example.bancario.model.StatusTransacao;
import com.example.bancario.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponseDTO(Long id, BigDecimal valor,
                                   StatusTransacao status,
                                   LocalDateTime concluidaEm) {
    public TransacaoResponseDTO(Transacao transacao){
        this(transacao.getId(), transacao.getValor(), transacao.getStatus(), transacao.getConcluidaEm());
    }
}
