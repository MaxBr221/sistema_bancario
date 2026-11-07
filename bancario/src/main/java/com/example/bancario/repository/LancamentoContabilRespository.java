package com.example.bancario.repository;

import com.example.bancario.model.LancamentoContabil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoContabilRespository extends JpaRepository<LancamentoContabil, Long> {
}
