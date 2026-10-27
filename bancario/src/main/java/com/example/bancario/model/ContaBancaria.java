
package com.example.bancario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "contas_bancarias")
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, unique = true, length = 10)
    private String agencia;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoConta tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConta status = StatusConta.ATIVA;

    @Version
    private Long versao;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadaEm = LocalDateTime.now();
}
