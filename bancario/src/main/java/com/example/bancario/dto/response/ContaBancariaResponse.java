package com.example.bancario.dto.response;

import com.example.bancario.model.Cliente;
import com.example.bancario.model.ContaBancaria;
import com.example.bancario.model.StatusConta;
import com.example.bancario.model.TipoConta;

import java.time.LocalDateTime;

public record ContaBancariaResponse(Cliente cliente, String agencia, String numero, TipoConta tipo,
                                    StatusConta status, LocalDateTime criadaEm) {
    public ContaBancariaResponse(ContaBancaria contaSalva) {
        this(contaSalva.getCliente(), contaSalva.getAgencia(),contaSalva.getNumero(), contaSalva.getTipo(),
                contaSalva.getStatus(), contaSalva.getCriadaEm());
    }
}
