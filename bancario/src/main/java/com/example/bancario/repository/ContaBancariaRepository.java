package com.example.bancario.repository;

import com.example.bancario.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
    boolean existsByNumero(String numero);
    Optional<ContaBancaria> findClienteById(Long idCliente);
}
