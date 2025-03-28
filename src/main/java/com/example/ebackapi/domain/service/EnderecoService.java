package com.example.ebackapi.domain.service;

import com.example.ebackapi.domain.model.Endereco;
import com.example.ebackapi.domain.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Listar todos os endereços
    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    // Buscar endereço por ID
    public Optional<Endereco> buscarPorId(UUID id) {
        return enderecoRepository.findById(id);
    }

    // Salvar um novo endereço ou atualizar
    public Endereco salvar(Endereco endereco) {
        // Não há necessidade de gerar UUID manualmente, já que o JPA cuida disso
        // Remover essa lógica de geração de UUID manualmente, pois @GeneratedValue cuida disso

        // Se o ID já existe, o Hibernate irá tratar como uma atualização.
        // Se o ID não existe, será tratada como uma inserção nova.
        return enderecoRepository.save(endereco);
    }

    // Deletar um endereço
    public void deletar(UUID id) {
        // Verificar se o endereço existe antes de tentar deletar
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Endereço com o ID fornecido não existe.");
        }
    }
}
