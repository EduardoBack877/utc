package com.example.ebackapi.domain.controller;

import com.example.ebackapi.domain.model.Endereco;
import com.example.ebackapi.domain.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Operações relacionadas aos endereços")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;


    @Autowired
    private RestTemplate restTemplate;

    // Listar todos os endereços
    @GetMapping
    @Operation(summary = "Listar todos os endereços")
    public ResponseEntity<List<Endereco>> listarTodos() {
        List<Endereco> enderecos = enderecoService.listarTodos();
        return enderecos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(enderecos);
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable UUID id) {
        Optional<Endereco> endereco = enderecoService.buscarPorId(id);
        return endereco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo endereço")
    public ResponseEntity<?> criar(@RequestBody Endereco endereco) {
        try {
            // Verifica se o CEP existe na API
            if (!verificarCepExistente(endereco.getCep())) {
                // Se o CEP não for encontrado, retorna um erro de CEP inválido com uma mensagem
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("CEP inválido"); // Retorna a mensagem "CEP inválido"
            }
            
            // Se o CEP for válido, salva o endereço no banco de dados
            Endereco novoEndereco = enderecoService.salvar(endereco);
            
            // Retorna a resposta com o status 201 (Criado) e o novo endereço
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
        } catch (Exception e) {
            // Loga o erro e retorna um erro genérico para o cliente
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // Método para verificar se o CEP existe na API
    private boolean verificarCepExistente(String cep) {
        String url = "https://brasilapi.com.br/api/cep/v1/" + cep;
        try {
            // Faz a requisição à API BrasilAPI para verificar a existência do CEP
            restTemplate.getForObject(url, String.class); // Se o CEP for válido, não lança exceção
            return true; // Se o CEP existir, retorna true
        } catch (Exception e) {
            // Se houver erro (CEP não encontrado), retorna false
            return false;
        }
    }

    // Atualizar um endereço existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um endereço existente")
    public ResponseEntity<Endereco> atualizar(@PathVariable UUID id, @RequestBody Endereco enderecoAtualizado) {
        Optional<Endereco> enderecoExistente = enderecoService.buscarPorId(id);
        
        if (enderecoExistente.isPresent()) {
            Endereco endereco = enderecoExistente.get();
            
            // Atualiza os campos não nulos do endereço
            if (enderecoAtualizado.getDescricao() != null) endereco.setDescricao(enderecoAtualizado.getDescricao());
            if (enderecoAtualizado.getCep() != null) endereco.setCep(enderecoAtualizado.getCep());
            if (enderecoAtualizado.getNumero() != null) endereco.setNumero(enderecoAtualizado.getNumero());
            if (enderecoAtualizado.getComplemento() != null) endereco.setComplemento(enderecoAtualizado.getComplemento());
            if (enderecoAtualizado.getBairro() != null) endereco.setBairro(enderecoAtualizado.getBairro());
            if (enderecoAtualizado.getUf() != null) endereco.setUf(enderecoAtualizado.getUf());
            if (enderecoAtualizado.getPais() != null) endereco.setPais(enderecoAtualizado.getPais());
    
            return ResponseEntity.ok(enderecoService.salvar(endereco));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar um endereço
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um endereço")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        Optional<Endereco> endereco = enderecoService.buscarPorId(id);
        if (endereco.isPresent()) {
            enderecoService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
