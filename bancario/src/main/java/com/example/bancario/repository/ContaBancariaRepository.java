package com.example.bancario.repository;

import com.example.bancario.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
    boolean existsByNumero(String numero);
}
