package com.example.ebackapi.domain.repository;

import com.example.ebackapi.domain.model.ClasseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, UUID> {
    @Query("SELECT c.descricao, SUM(f.peso) FROM Fardo f JOIN f.classe c WHERE c.ativo = 1 GROUP BY c.descricao")
    List<Object[]> findPesoTotalCompradoPorClasse();

}