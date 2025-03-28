package com.example.ebackapi.domain.repository;

import com.example.ebackapi.domain.model.Fardo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.UUID;

@Repository
public interface FardoRepository extends JpaRepository<Fardo, UUID> { // Alterado String para UUID
    Optional<Fardo> findByNumero(int numero);

    @Query("SELECT SUM(f.peso) FROM Fardo f")
    Double getPesoTotalGeral();

     // Consulta para somar o peso dos fardos entre duas datas
    @Query("SELECT SUM(f.peso) FROM Fardo f WHERE f.dataHoraCompra BETWEEN :dataInicial AND :dataFinal")
    Double getPesoTotalGeralPorData(LocalDateTime dataInicial, LocalDateTime dataFinal);

    // Consulta para somar o peso dos fardos após uma data
    @Query("SELECT SUM(f.peso) FROM Fardo f WHERE f.dataHoraCompra >= :dataInicial")
    Double getPesoTotalGeralPorDataInicial(LocalDateTime dataInicial);

    // Consulta para somar o peso dos fardos até uma data
    @Query("SELECT SUM(f.peso) FROM Fardo f WHERE f.dataHoraCompra <= :dataFinal")
    Double getPesoTotalGeralPorDataFinal(LocalDateTime dataFinal);


}