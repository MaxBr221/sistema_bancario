package com.example.bancario.repository;

import com.example.bancario.model.ContaContabil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaContabilRepository extends JpaRepository<ContaContabil, Long> {
    Optional<ContaContabil> findByCodigo(String codigo);
}
