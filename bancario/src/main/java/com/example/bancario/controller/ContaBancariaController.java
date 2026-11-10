package com.example.bancario.controller;

import com.example.bancario.dto.TransacaoResponseDTO;
import com.example.bancario.service.ContaBancariaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("contaBacaria")
@RequiredArgsConstructor
@Slf4j
public class ContaBancariaController {
    private final ContaBancariaService contaBancariaService;

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> deposito(@PathVariable Long id, @RequestBody BigDecimal valorDeposito){
        TransacaoResponseDTO transacaoDTO = contaBancariaService.deposito(id, valorDeposito);
        log.info("Deposito no id {}, e na data {} efetuando.", id, transacaoDTO.concluidaEm());
        return ResponseEntity.ok(transacaoDTO);
    }
    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> saque(@PathVariable Long id, @RequestBody BigDecimal valorSaque){
        TransacaoResponseDTO transacaoDTO = contaBancariaService.saque(id, valorSaque);
        log.info("Saque no id {}, efetuado no data {}", id, transacaoDTO.concluidaEm());
        return ResponseEntity.ok(transacaoDTO);
    }
}
