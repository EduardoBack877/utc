package com.example.ebackapi.domain.controller;

import com.example.ebackapi.domain.model.Fardo;
import com.example.ebackapi.domain.service.ClasseService;
import com.example.ebackapi.domain.service.FardoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/fardos")
public class FardoController {

    private final FardoService fardoService;
    private final ClasseService classeService;

    public FardoController(FardoService fardoService, ClasseService classeService) {
        this.fardoService = fardoService;
        this.classeService = classeService;
    }

    

    @GetMapping
    public ResponseEntity<List<Fardo>> listarTodos() {
        return ResponseEntity.ok(fardoService.listarTodos());
    }

    @GetMapping("/pesogeralfardos")
    public ResponseEntity<Double> getPesoGeralFardos(
            @RequestParam(value = "datainicial", required = false) LocalDateTime dataInicial,
            @RequestParam(value = "datafinal", required = false) LocalDateTime dataFinal) {

        // Verifica se as datas foram fornecidas. Caso não, utiliza intervalo de datas válido.
        Double pesoTotal = fardoService.getPesoTotalGeralPorData(dataInicial, dataFinal);
        return ResponseEntity.ok(pesoTotal);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fardo> buscarPorId(@PathVariable UUID id) {
        return fardoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/peso-total-classes")
    public ResponseEntity<List<Object[]>> getPesoTotalPorClasse() {
        List<Object[]> pesoTotalPorClasse = classeService.getPesoTotalPorClasse();
        return ResponseEntity.ok(pesoTotalPorClasse);
    }


    
    

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletar(@PathVariable int numero) {
        Optional<Fardo> fardoOpt = fardoService.buscarPorNumero(numero);

        if (fardoOpt.isPresent()) {
            fardoService.deletar(fardoOpt.get().getFardoUID());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{numero}")
    public ResponseEntity<Fardo> atualizar(@PathVariable int numero, @RequestBody Fardo fardoAtualizado) {
        Optional<Fardo> fardoExistente = fardoService.buscarPorNumero(numero);

        if (fardoExistente.isPresent()) {
            Fardo fardo = fardoExistente.get();

            if (fardoAtualizado.getDataHoraCompra() != null) fardo.setDataHoraCompra(fardoAtualizado.getDataHoraCompra());
            if (fardoAtualizado.getPessoa() != null) fardo.setPessoa(fardoAtualizado.getPessoa());
            if (fardoAtualizado.getClasse() != null) fardo.setClasse(fardoAtualizado.getClasse());
            if (fardoAtualizado.getPeso() != 0) fardo.setPeso(fardoAtualizado.getPeso());
            if (fardoAtualizado.getAtivo() != 0) fardo.setAtivo(fardoAtualizado.getAtivo());

            return ResponseEntity.ok(fardoService.salvar(fardo));
        }
        return ResponseEntity.notFound().build();
    }
}
