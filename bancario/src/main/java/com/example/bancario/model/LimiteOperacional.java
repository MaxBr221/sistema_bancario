
package com.example.bancario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "limites_operacionais")
public class LimiteOperacional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false, unique = true)
    private ContaBancaria conta;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal limiteSaqueDiario =
            new BigDecimal("1000.00");

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal limiteTransferenciaDiaria =
            new BigDecimal("5000.00");

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal limitePorTransferencia =
            new BigDecimal("2000.00");

    @Column(nullable = false)
    private LocalDateTime atualizadoEm = LocalDateTime.now();
}
