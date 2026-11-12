package com.example.bancario.repository;

import com.example.bancario.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<UserDetails> findClienteByLogin(String login);
    boolean existsByCpf(String cpf);
}
