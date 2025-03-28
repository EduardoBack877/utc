package com.example.ebackapi.domain.controller;

import com.example.ebackapi.domain.model.Endereco;
import com.example.ebackapi.domain.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Listar todos os endereços
    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {
        return ResponseEntity.ok(enderecoService.listarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable UUID id) {
        Optional<Endereco> endereco = enderecoService.buscarPorId(id);
        return endereco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar um novo endereço
    @PostMapping
    public ResponseEntity<Endereco> criar(@RequestBody Endereco endereco) {
        try {
            // Salva o endereço utilizando o serviço
            Endereco novoEndereco = enderecoService.salvar(endereco);
            // Retorna a resposta com status 201 Created e o corpo com o novo endereço
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
        } catch (Exception e) {
            e.printStackTrace();  // Imprime o erro no console para debug
            // Retorna uma resposta de erro 500 caso haja algum problema
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Atualizar um endereço existente
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable UUID id, @RequestBody Endereco enderecoAtualizado) {
        Optional<Endereco> enderecoExistente = enderecoService.buscarPorId(id);
        
        if (enderecoExistente.isPresent()) {
            Endereco endereco = enderecoExistente.get();
    
            // Atualiza apenas os campos não nulos
            if (enderecoAtualizado.getDescricao() != null) {
                endereco.setDescricao(enderecoAtualizado.getDescricao());
            }
            if (enderecoAtualizado.getCep() != null) {
                endereco.setCep(enderecoAtualizado.getCep());
            }
            if (enderecoAtualizado.getNumero() != null) {
                endereco.setNumero(enderecoAtualizado.getNumero());
            }
            if (enderecoAtualizado.getComplemento() != null) {
                endereco.setComplemento(enderecoAtualizado.getComplemento());
            }
            if (enderecoAtualizado.getBairro() != null) {
                endereco.setBairro(enderecoAtualizado.getBairro());
            }
            if (enderecoAtualizado.getUf() != null) {
                endereco.setUf(enderecoAtualizado.getUf());
            }
            if (enderecoAtualizado.getPais() != null) {
                endereco.setPais(enderecoAtualizado.getPais());
            }
    
            return ResponseEntity.ok(enderecoService.salvar(endereco));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar um endereço
    @DeleteMapping("/{id}")
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
