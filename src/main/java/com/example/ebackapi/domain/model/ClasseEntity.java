package com.example.ebackapi.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "classe") // Define explicitamente o nome da tabela
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClasseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Deixe o Hibernate escolher o melhor tipo
    private UUID classeUID;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, unique = true)
    private int codigo;

    @Column(name = "classe_interna", nullable = false)
    private int classeInterna; // Define se é uma classe interna ou não

    @Column(nullable = false)
    private int ativo;
}