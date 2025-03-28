package com.example.ebackapi.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pessoa") // Define explicitamente o nome da tabela
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // UUID gerado automaticamente
    private UUID pessoaUID;

    @Column(nullable = false, unique = true)
    private int codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private LocalDate dataNascimento; // Tipo correto para datas

    // Mapeamento direto com UUID
    @Column(name = "enderecoUID", nullable = false)
    private UUID enderecoUID; // Armazenando o UUID diretamente

    @OneToOne
    @JoinColumn(name = "enderecoUID", referencedColumnName = "enderecoUID", insertable = false, updatable = false)
    private Endereco endereco; // Relacionamento correto com Endereco

    @Column(nullable = false)
    private int ativo;
}