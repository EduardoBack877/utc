package com.example.ebackapi.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;


@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  
    @Column(nullable = false, unique = true, columnDefinition = "CHAR(36)")  
    private UUID enderecoUID;  

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, length = 10)
    private String cep;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(length = 255)
    private String complemento;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 100)
    private String pais;

    // Controle de versão otimista
    @Version
    @Column(nullable = false)
    private Long version;
}
