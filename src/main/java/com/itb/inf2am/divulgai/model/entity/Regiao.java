package com.itb.inf2am.divulgai.model.entity;

import jakarta.persistence.*;

@Entity

public class Regiao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String cidade;

    @Column(length = 2, nullable = false)
    private String uf;

    @Column(length = 20, nullable = true)
    private String zona;

    @Column(length = 200, nullable = false)
    private String descricao;

    @Column(name = "status_regiao", length = 20, nullable = false)
    private String statusRegiao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatusRegiao() {
        return statusRegiao;
    }

    public void setStatusRegiao(String statusRegiao) {
        this.statusRegiao = statusRegiao;
    }
}
