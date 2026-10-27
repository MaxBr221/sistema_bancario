
package com.example.bancario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "credenciais")
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, unique = true)
    private Cliente cliente;

    @Column(nullable = false, unique = true, length = 150)
    private String emailLogin;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    private LocalDateTime atualizadoEm;
}
