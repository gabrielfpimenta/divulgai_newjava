package com.itb.inf2am.divulgai.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = 100, nullable = false)
    private String nome;

    @Column (name="status_categoria", nullable = false)
    private boolean statusCategoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public boolean isStatusCategoria() {
        return statusCategoria;
    }

    public void setStatusCategoria(boolean statusCategoria) {
        this.statusCategoria = statusCategoria;
    }
}
