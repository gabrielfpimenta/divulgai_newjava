package com.itb.inf2am.divulgai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itb.inf2am.divulgai.model.entity.Categoria;
import com.itb.inf2am.divulgai.model.services.CategoriaService;

// Getter (get): Apenas lê o valor do atributo.
// Setter (set): Apenas modifica o valor do atributo.
@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    } // Service, não repository

    @GetMapping
    public List<Categoria> findAll() {
        return categoriaService.findAll(); // chama o service
    }

    @PostMapping
    public Categoria create(@RequestBody Categoria categoria) {
        return categoriaService.save(categoria); // chama o service
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarCategoriaPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoriaService.findById(id));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Categoria não encontrada com o id " + id
                    )
            );

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            Categoria categoriaExistente = categoriaService.findById(id); // já lança exceção se não achar

            categoriaExistente.setNome(categoria.getNome());

            Categoria categoriaAtualizada = categoriaService.save(categoriaExistente);

            return ResponseEntity.ok(categoriaAtualizada);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Categoria não encontrada com o id " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirCategoria(@PathVariable Long id) {
        try {
            categoriaService.delete(id); // chama o service
            return ResponseEntity.ok(Map.of("message", "Categoria deletada com sucesso"));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Categoria não encontrada com o id " + id
                    )
            );
        }
    }
}
