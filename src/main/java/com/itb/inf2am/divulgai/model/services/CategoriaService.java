package com.itb.inf2am.divulgai.model.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itb.inf2am.divulgai.model.entity.Categoria;
import com.itb.inf2am.divulgai.model.repository.CategoriaRepository;

@Service
public class CategoriaService {

    // Injeção de dependência
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Método responsável em listar todos as Categorias cadastradas no banco de dados
    //READ DO CRUD
    public List<Categoria> findAll() {

        return categoriaRepository.findAll();
    }

    // Método responsável em criar a Categoria no banco de dados
    //CREATE DO CRUD
    public Categoria save(Categoria Categoria) {
        Categoria.setStatusCategoria(true);
        return categoriaRepository.save(Categoria);
    }

    // Método responsável em listar o categoria por ID
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o id " + id));
    }

    // Método responsável em atualizar em atualizar a categoria
    public Categoria update(Long id, Categoria categoria) {
        Categoria categoriaExistente = findById(id);
        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setStatusCategoria(categoria.isStatusCategoria());

        return categoriaRepository.save(categoriaExistente);
    }

    // Método responsável em excluir a categoria ( exclusão física )
    public void delete(Long id) {

        Categoria categoriaExistente = findById(id);
        categoriaRepository.delete(categoriaExistente);
    }
}
