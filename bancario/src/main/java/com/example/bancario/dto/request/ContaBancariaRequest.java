package com.example.bancario.dto.request;

import com.example.bancario.model.Cliente;
import com.example.bancario.model.TipoConta;

public record ContaBancariaRequest(Cliente cliente, String agencia, String numero, TipoConta tipo
) {
}
