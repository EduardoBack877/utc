package com.example.ebackapi.domain.service;

import com.example.ebackapi.domain.model.Pessoa;
import com.example.ebackapi.domain.model.Endereco;
import com.example.ebackapi.domain.repository.PessoaRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.ebackapi.domain.repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository; // Adicionar a injeção de dependência do EnderecoRepository

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(UUID id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public Pessoa salvar(Pessoa pessoa, String enderecoUID) {
        if (enderecoUID != null && !enderecoUID.isEmpty()) {
            UUID enderecoUUID = UUID.fromString(enderecoUID); // Converter String para UUID
    
            // Buscar o endereço no banco de dados
            Optional<Endereco> enderecoOpt = enderecoRepository.findById(enderecoUUID);
            if (enderecoOpt.isPresent()) {
                pessoa.setEndereco(enderecoOpt.get()); // Associar o Endereco encontrado
            } else {
                throw new EntityNotFoundException("Endereço não encontrado.");
            }
        }
        return pessoaRepository.save(pessoa);
    }

    public void deletar(UUID id) {
        pessoaRepository.deleteById(id);
    }

    public List<Object[]> getPesoTotalCompradoPorPessoa() {
        return pessoaRepository.findPesoTotalCompradoPorPessoa();
    }

    public Optional<Pessoa> buscarPorCodigo(int codigo) {
        return pessoaRepository.findByCodigo(codigo);
    }

    

    public void deletarPorCodigo(int codigo) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findByCodigo(codigo);
        if (pessoaOpt.isPresent()) {
            pessoaRepository.delete(pessoaOpt.get());  // Deletar a pessoa usando a entidade encontrada
        }
    }
}
