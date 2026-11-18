package com.example.bancario.repository;

import com.example.bancario.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findContaBancariaByNumero(String numero);
}
