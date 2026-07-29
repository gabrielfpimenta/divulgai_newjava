package com.itb.inf2am.divulgai.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;


    @Column(name="nota", nullable = false)
    private Integer nota;

    @Column(length = 200, nullable = false)
    private String titulo;

    @Column(length = 200, nullable = false)
   private String descricao;


    @Column(name = "tipo_feedback", length = 50, nullable = false)
    private String tipoFeedback;


    @Column(name = "data_cadastro", nullable = false)
   private LocalDateTime dataCadastro;

    @Column(name = "status_feedback", length = 20, nullable = false)
   private String statusFeedback;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "prestador_id", nullable = false)
    private Long prestadorId;

    // getters e setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getPrestadorId() { return prestadorId; }
    public void setPrestadorId(Long prestadorId) { this.prestadorId = prestadorId; }

    public Long getId() {
        return id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoFeedback() {
        return tipoFeedback;
    }

    public void setTipoFeedback(String tipoFeedback) {
        this.tipoFeedback = tipoFeedback;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatusFeedback() {
        return statusFeedback;
    }

    public void setStatusFeedback(String statusFeedback) {
        this.statusFeedback = statusFeedback;
    }
}
