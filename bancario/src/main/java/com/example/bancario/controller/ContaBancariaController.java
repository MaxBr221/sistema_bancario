package com.example.bancario.controller;

import com.example.bancario.dto.request.ContaBancariaRequest;
import com.example.bancario.dto.response.ContaBancariaResponse;
import com.example.bancario.dto.response.TransacaoResponseDTO;
import com.example.bancario.service.ContaBancariaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contaBacaria")
@RequiredArgsConstructor
@Slf4j
public class ContaBancariaController {
    private final ContaBancariaService contaBancariaService;

    @PostMapping("/criarConta")
    public ResponseEntity<ContaBancariaResponse> criarConta(@RequestBody ContaBancariaRequest contaBancariaRequest){
        ContaBancariaResponse contaBancariaResponse = contaBancariaService.criarContaBancaria(contaBancariaRequest);
        log.info("Criando conta do cliente com o cpf: {}, na agência {}", contaBancariaRequest.cliente().getCpf(),
                contaBancariaRequest.agencia());
        return ResponseEntity.status(HttpStatus.CREATED).body(contaBancariaResponse);
    }

    @PostMapping("/deposito")
    public ResponseEntity<TransacaoResponseDTO> deposito(@PathVariable Long id, @RequestBody BigDecimal valorDeposito){
        TransacaoResponseDTO transacaoDTO = contaBancariaService.deposito(id, valorDeposito);
        log.info("Deposito no id {}, e na data {} efetuando.", id, transacaoDTO.concluidaEm());
        return ResponseEntity.ok(transacaoDTO);
    }
    @PostMapping("/saque")
    public ResponseEntity<TransacaoResponseDTO> saque(@PathVariable Long id, @RequestBody BigDecimal valorSaque){
        TransacaoResponseDTO transacaoDTO = contaBancariaService.saque(id, valorSaque);
        log.info("Saque no id {}, efetuado no data {}", id, transacaoDTO.concluidaEm());
        return ResponseEntity.ok(transacaoDTO);
    }
    @PostMapping("/transferencia")
    public ResponseEntity<TransacaoResponseDTO> transferencia(@PathVariable Long id, @RequestBody BigDecimal valorTransferencia){
        TransacaoResponseDTO transacaoDTO = contaBancariaService.transferencia(id, valorTransferencia);
        log.info("Transferencia no id:{}, na data de:{}", id, transacaoDTO.concluidaEm());
        return ResponseEntity.ok(transacaoDTO);
    }
    @GetMapping("/conta/{numero}")
    public ResponseEntity<ContaBancariaResponse> consultaConta(@PathVariable String numero){
        ContaBancariaResponse contaDTO = contaBancariaService.consultaConta(numero);
        log.info("Buscando conta do cliente: {}", contaDTO.cliente().getNomeCompleto());
        return ResponseEntity.ok(contaDTO);
    }
    @GetMapping("/saldo/{numero}")
    public ResponseEntity<BigDecimal> consultaSaldo(@PathVariable String numero){
        BigDecimal saldo = contaBancariaService.consultaSaldo(numero);
        log.info("Buscando saldo");
        return ResponseEntity.ok(saldo);
    }
    @GetMapping("/extrato/{numero}")
    public ResponseEntity<List<TransacaoResponseDTO>> extratoBancario(@PathVariable String numero){
        List<TransacaoResponseDTO> extratoTransacoes = contaBancariaService.extratoBancario(numero);
        for (var t: extratoTransacoes){
            log.info("Listando extrato da data: {}", t.concluidaEm());
        }
        return ResponseEntity.ok(extratoTransacoes);
    }
    @DeleteMapping("/desativar/{numero}")
    public ResponseEntity<Void> desativarConta(@PathVariable String numero){
        contaBancariaService.desativarConta(numero);
        log.info("Desativando conta");
        return ResponseEntity.noContent().build();
    }
}
