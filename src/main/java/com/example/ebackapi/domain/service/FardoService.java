package com.example.ebackapi.domain.service;

import com.example.ebackapi.domain.model.Fardo;
import com.example.ebackapi.domain.repository.FardoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FardoService {

    @Autowired
    private FardoRepository fardoRepository;

    public List<Fardo> listarTodos() {
        return fardoRepository.findAll();
    }

    public Optional<Fardo> buscarPorId(UUID id) { 
        return fardoRepository.findById(id);
    }

    public Optional<Fardo> buscarPorNumero(int numero) {
        return fardoRepository.findByNumero(numero);
    }

    public Double getPesoTotalGeral() {
        return fardoRepository.getPesoTotalGeral();
    }
public Double getPesoTotalGeralPorData(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            return fardoRepository.getPesoTotalGeralPorData(dataInicial, dataFinal);
        } else if (dataInicial != null) {
            // Se somente dataInicial for fornecida, filtra fardos desde essa data
            return fardoRepository.getPesoTotalGeralPorDataInicial(dataInicial);
        } else if (dataFinal != null) {
            // Se somente dataFinal for fornecida, filtra fardos até essa data
            return fardoRepository.getPesoTotalGeralPorDataFinal(dataFinal);
        } else {
            // Se nenhuma data for fornecida, retorna o peso total de todos os fardos
            return fardoRepository.getPesoTotalGeral();
        }
    }


    public Fardo salvar(Fardo fardo) {
        return fardoRepository.save(fardo);
    }

    public void deletar(UUID id) { // Alterado de String para UUID
        fardoRepository.deleteById(id);
    }
}