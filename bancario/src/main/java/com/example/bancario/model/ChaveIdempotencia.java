
package com.example.bancario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "chaves_idempotencia",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_idempotencia_usuario_chave",
                        columnNames = {"escopo", "chave"}
                )
        }
)
public class ChaveIdempotencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ex.: ID do cliente autenticado que realizou a requisição
    @Column(nullable = false, length = 100)
    private String escopo;

    @Column(nullable = false, length = 100)
    private String chave;

    @Column(nullable = false, length = 64)
    private String hashRequisicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTransacao status = StatusTransacao.PENDENTE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id", unique = true)
    private Transacao transacao;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadaEm = LocalDateTime.now();

    private LocalDateTime expiraEm;
}
