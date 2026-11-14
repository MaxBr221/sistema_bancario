package com.example.bancario.service;

import com.example.bancario.dto.request.ContaBancariaRequest;
import com.example.bancario.dto.response.TransacaoResponseDTO;
import com.example.bancario.model.*;
import com.example.bancario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContaBancariaService {
    private final ContaBancariaRepository contaBancariaRepository;
    private final TransacaoRepository transacaoRepository;
    private final LancamentoContabilRespository lancamentoContabilRespository;
    private final ContaContabilRepository contaContabilRepository;
    private final ClienteRepository clienteRepository;


    @Transactional
    public void criarContaBancaria(ContaBancariaRequest contaBancariaRequest){
        if(contaBancariaRepository.existsByNumero(contaBancariaRequest.numero())){
            throw new IllegalArgumentException("Conta já criada!");
        }
        if(!clienteRepository.existsByCpf(contaBancariaRequest.cliente().getCpf())){
            throw new UsernameNotFoundException("Cliente não ativo ainda!");
        }

        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setCliente(contaBancaria.getCliente());
        contaBancaria.setNumero(contaBancaria.getNumero());
        contaBancaria.setAgencia(contaBancaria.getAgencia());
        contaBancaria.setTipo(contaBancaria.getTipo());
        contaBancaria.setCriadaEm(LocalDateTime.now());
        contaBancaria.setStatus(StatusConta.ATIVA);
        contaBancariaRepository.save(contaBancaria);
    }
    @Transactional
    public TransacaoResponseDTO deposito(Long id, BigDecimal valorDeposito){
        ContaBancaria contaBancaria = contaBancariaRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Conta Bancaria não encontrada!"));
        isAtiva(contaBancaria);

        if(valorDeposito.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Não é possivel depositar valor menor ou igual a zero.");
        }
        ContaContabil contaCaixa =
                contaContabilRepository.findByCodigo("1.01")
                        .orElseThrow(() -> new RuntimeException("Conta Caixa não encontrada"));

        ContaContabil contaDepositosClientes =
                contaContabilRepository.findByCodigo("2.01")
                        .orElseThrow(() -> new RuntimeException("Conta de depósitos não encontrada"));

        Transacao transacao = new Transacao();

        transacao.setContaDestino(contaBancaria);
        transacao.setStatus(StatusTransacao.PENDENTE);
        transacao.setCriadaEm(LocalDateTime.now());
        transacao.setValor(valorDeposito);
        transacao.setTipo(TipoTransacao.DEPOSITO);
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        salvarLancamentoContabil(transacao, valorDeposito, contaCaixa, contaDepositosClientes);

        transacaoSalva.setStatus(StatusTransacao.CONCLUIDA);
        transacaoSalva.setConcluidaEm(LocalDateTime.now());
        transacaoRepository.save(transacaoSalva);

        BigDecimal saldo = transacao.getValor().add(valorDeposito);
        contaBancaria.setSaldo(saldo);
        contaBancariaRepository.save(contaBancaria);

        return new TransacaoResponseDTO(transacaoSalva);
    }
    @Transactional
    public TransacaoResponseDTO saque(Long id, BigDecimal valorSaque){
        ContaBancaria contaBancaria = contaBancariaRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Conta Bancaria não encontrada!"));
        isAtiva(contaBancaria);

        Transacao transacao = new Transacao();

        ContaContabil contaCaixa =
                contaContabilRepository.findByCodigo("1.01")
                        .orElseThrow(() -> new RuntimeException("Conta Caixa não encontrada"));

        ContaContabil contaDepositosClientes =
                contaContabilRepository.findByCodigo("2.01")
                        .orElseThrow(() -> new RuntimeException("Conta de depósitos não encontrada"));

        if(valorSaque.compareTo(transacao.getValor()) > 0){
            throw new IllegalArgumentException("Valor de saque maior que valor disponivel em conta.");
        }
        transacao.setStatus(StatusTransacao.PENDENTE);
        transacao.setValor(valorSaque);
        transacao.setCriadaEm(LocalDateTime.now());
        transacao.setTipo(TipoTransacao.SAQUE);
        Transacao transacaoSalva = transacaoRepository.save(transacao);
        salvarLancamentoContabil(transacao, valorSaque, contaCaixa, contaDepositosClientes);
        transacaoSalva.setStatus(StatusTransacao.CONCLUIDA);
        transacaoSalva.setConcluidaEm(LocalDateTime.now());
        transacaoRepository.save(transacaoSalva);

        BigDecimal saldo = transacao.getValor().subtract(valorSaque);
        contaBancaria.setSaldo(saldo);
        contaBancariaRepository.save(contaBancaria);

        return new TransacaoResponseDTO(transacaoSalva);
    }
    @Transactional
    public TransacaoResponseDTO transferencia(Long idContaDestino, BigDecimal valorTransferencia){
        // verificar se a conta de origem está valida com auth
        ContaBancaria contaBancaria = contaBancariaRepository.findById(idContaDestino)
                .orElseThrow(() -> new UsernameNotFoundException("Conta Bancaria não encontrada!"));
        isAtiva(contaBancaria);

        ContaContabil contaCaixa =
                contaContabilRepository.findByCodigo("1.01")
                        .orElseThrow(() -> new RuntimeException("Conta Caixa não encontrada"));

        ContaContabil contaDepositosClientes =
                contaContabilRepository.findByCodigo("2.01")
                        .orElseThrow(() -> new RuntimeException("Conta de depósitos não encontrada"));

        Transacao transacao = new Transacao();

        transacao.setContaDestino(contaBancaria);
        transacao.setCriadaEm(LocalDateTime.now());
        transacao.setTipo(TipoTransacao.TRANSFERENCIA);
        transacao.setValor(valorTransferencia);
        transacao.setStatus(StatusTransacao.PENDENTE);
       // transacao.setContaOrigem();
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        salvarLancamentoContabil(transacao, valorTransferencia, contaCaixa, contaDepositosClientes);

        transacaoSalva.setConcluidaEm(LocalDateTime.now());
        transacaoSalva.setStatus(StatusTransacao.CONCLUIDA);

        transacaoRepository.save(transacaoSalva);

        BigDecimal saldo = transacao.getValor().add(valorTransferencia);
        contaBancaria.setSaldo(saldo);
        contaBancariaRepository.save(contaBancaria);

        return new TransacaoResponseDTO(transacaoSalva);
    }
    private void isAtiva(ContaBancaria contaBancaria){
        Cliente cliente = contaBancaria.getCliente();
        if(cliente.getStatusConta() != StatusConta.ATIVA){
            throw new IllegalArgumentException("Cliente não está ativo!");
        }
        if(contaBancaria.getStatus() != StatusConta.ATIVA){
            throw new IllegalArgumentException("Erro, Conta Bancaria não ativa!");
        }
    }
    private void salvarLancamentoContabil(Transacao transacao, BigDecimal valorDeposito, ContaContabil contaCaixa, ContaContabil contaDepositosClientes){
        LancamentoContabil debito = new LancamentoContabil();
        debito.setTransacao(transacao);
        debito.setValor(valorDeposito);
        debito.setContaContabil(contaCaixa);
        debito.setNatureza(TipoLancamento.DEBITO);
        debito.setCriadoEm(LocalDateTime.now());

        LancamentoContabil credito = new LancamentoContabil();
        credito.setTransacao(transacao);
        credito.setValor(valorDeposito);
        credito.setContaContabil(contaDepositosClientes);
        credito.setNatureza(TipoLancamento.CREDITO);
        credito.setCriadoEm(LocalDateTime.now());

        lancamentoContabilRespository.save(debito);
        lancamentoContabilRespository.save(credito);
    }
}
