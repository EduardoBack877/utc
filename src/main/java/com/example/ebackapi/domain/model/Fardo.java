package com.example.ebackapi.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fardo") // Define explicitamente o nome da tabela
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fardo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // O Hibernate escolhe o melhor tipo
    private UUID fardoUID;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private LocalDateTime dataHoraCompra; // Tipo correto para data e hora

    @ManyToOne
    @JoinColumn(name = "pessoaUID", nullable = false)
    private Pessoa pessoa; // Relacionamento correto com Pessoa

    @ManyToOne
    @JoinColumn(name = "classeUID", nullable = false)
    private ClasseEntity classe; // Relacionamento correto com Classe

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private int ativo;
}