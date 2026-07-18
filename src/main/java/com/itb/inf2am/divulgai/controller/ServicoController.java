package com.itb.inf2am.divulgai.controller;

import com.itb.inf2am.divulgai.dto.ServicoDTO;
import com.itb.inf2am.divulgai.dto.ServicoPesquisaDTO;
import com.itb.inf2am.divulgai.model.entity.Servico;
import com.itb.inf2am.divulgai.model.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/servico")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public Page<ServicoPesquisaDTO> pesquisar(
            @RequestParam(required = false) String nomeServico,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String uf,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return servicoService.pesquisar(nomeServico, categoriaId, cidade, uf, orderBy, page, size);
    }

    @GetMapping("/all")
    public List<Servico> findAll() {
        return servicoService.findAll();
    }

    @PostMapping
    public Servico create(@RequestBody ServicoDTO dto) {
        return servicoService.saveFromDTO(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarServicoPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(servicoService.findById(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "ID inválido: " + id
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", e.getMessage()
                    )
            );
        }
    }

    @PatchMapping("/{id}/contador")
    public ResponseEntity<Object> incrementarContador(@PathVariable Long id) {
        try {
            ServicoDTO servicoAtualizado = servicoService.incrementarContador(id);
            return ResponseEntity.ok(servicoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", e.getMessage()
                    )
            );
        }
    }
}