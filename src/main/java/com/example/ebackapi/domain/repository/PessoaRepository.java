package com.example.ebackapi.domain.repository;

import com.example.ebackapi.domain.model.Pessoa;
import com.example.ebackapi.domain.model.Fardo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
    
    Optional<Pessoa> findByCodigo(int codigo);

    // Corrigido a importação do @Query e a consulta
    @Query("SELECT p.nome, SUM(f.peso) FROM Fardo f JOIN f.pessoa p WHERE p.ativo = 1 GROUP BY p.nome")
    List<Object[]> findPesoTotalCompradoPorPessoa(); // Retorno da consulta em forma de lista de objetos
}
