package com.example.ebackapi.domain.controller;

import com.example.ebackapi.domain.model.Pessoa;
import com.example.ebackapi.domain.service.PessoaService;
import jakarta.validation.Valid; // Importação correta
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarTodas() {
        return ResponseEntity.ok(pessoaService.listarTodas());
    }


    @GetMapping("/{codigo}")
public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable int codigo) {
    return pessoaService.buscarPorCodigo(codigo)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
}


@PostMapping
public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, @RequestParam(required = false) String enderecoUID) {
    Pessoa novaPessoa = pessoaService.salvar(pessoa, enderecoUID); // Passando o enderecoUID
    URI uri = URI.create("/pessoas/" + novaPessoa.getPessoaUID());
    return ResponseEntity.created(uri).body(novaPessoa);
}

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable int codigo) {
        Optional<Pessoa> pessoaOpt = pessoaService.buscarPorCodigo(codigo);
        
        if (pessoaOpt.isPresent()) {
            pessoaService.deletarPorCodigo(codigo);  // Usando o método de deletar por código
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/peso-total")
    public ResponseEntity<List<Object[]>> listarPesoTotalCompradoPorPessoa() {
        List<Object[]> result = pessoaService.getPesoTotalCompradoPorPessoa();
        return ResponseEntity.ok(result);
    }

   /*  @PutMapping("/{codigo}")
public ResponseEntity<Pessoa> atualizar(@PathVariable int codigo, @RequestBody Pessoa pessoaAtualizada) {
    Optional<Pessoa> pessoaExistente = pessoaService.buscarPorCodigo(codigo);
    
    if (pessoaExistente.isPresent()) {
        Pessoa pessoa = pessoaExistente.get();

        // Atualiza apenas os campos enviados na requisição
        if (pessoaAtualizada.getNome() != null) pessoa.setNome(pessoaAtualizada.getNome());
        if (pessoaAtualizada.getDocumento() != null) pessoa.setDocumento(pessoaAtualizada.getDocumento());
        if (pessoaAtualizada.getDataNascimento() != null) pessoa.setDataNascimento(pessoaAtualizada.getDataNascimento());
        if (pessoaAtualizada.getEndereco() != null) pessoa.setEndereco(pessoaAtualizada.getEndereco());
        if (pessoaAtualizada.getAtivo() != 0) pessoa.setAtivo(pessoaAtualizada.getAtivo());

        return ResponseEntity.ok(pessoaService.salvar(pessoa));
    }
    
    return ResponseEntity.notFound().build();
}*/


}
