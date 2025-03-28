package com.example.ebackapi.domain.service;

import com.example.ebackapi.domain.model.Endereco;
import com.example.ebackapi.domain.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    
    private static final String URL_API_CEP = "https://brasilapi.com.br/api/cep/v1/";

    private final RestTemplate restTemplate = new RestTemplate();

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
    // Verificar se o CEP é válido na API
    if (!verificarCepExistente(endereco.getCep())) {
        throw new IllegalArgumentException("CEP inválido");
    }

    // Salvar o endereço no banco de dados. O JPA irá cuidar de identificar se é uma nova inserção ou atualização.
    return enderecoRepository.save(endereco);
}

  // Verificar se o CEP é válido na API
  public boolean verificarCepExistente(String cep) {
    String url = URL_API_CEP + cep;

    try {
        // Faz a requisição à API BrasilAPI
        restTemplate.getForObject(url, String.class); // Se o CEP for válido, não lança exceção
        return true; // Se a requisição for bem-sucedida, o CEP existe
    } catch (Exception e) {
        // Se houver erro (CEP não encontrado ou outra falha), retorna false
        return false;
    }
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
