
package com.example.bancario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contas_contabeis")
public class ContaContabil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(nullable = false, length = 120)
    private String nome;

    // Exemplos: ATIVO, PASSIVO, RECEITA, DESPESA
    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(nullable = false)
    private boolean ativa = true;
}
