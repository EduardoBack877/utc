package com.example.ebackapi.domain.service;

import com.example.ebackapi.domain.model.ClasseEntity;
import com.example.ebackapi.domain.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public List<ClasseEntity> listarTodas() {
        return classeRepository.findAll();
    }

    public Optional<ClasseEntity> buscarPorId(UUID id) { // Alterado de String para UUID
        return classeRepository.findById(id);
    }

    
    public List<Object[]> getPesoTotalPorClasse() {
        return classeRepository.findPesoTotalCompradoPorClasse();
    }

    public ClasseEntity salvar(ClasseEntity classe) {
        return classeRepository.save(classe);
    }

    public void deletar(UUID id) { // Alterado de String para UUID
        classeRepository.deleteById(id);
    }
}
