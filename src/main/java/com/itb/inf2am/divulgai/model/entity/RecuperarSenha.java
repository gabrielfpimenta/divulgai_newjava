package com.itb.inf2am.divulgai.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RecuperarSenha")
public class RecuperarSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 6)
    private String codigo;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(name = "expiracao_codigo")
    private LocalDateTime expiracaoCodigo;

    @Column(name = "status_codigo", nullable = false)
    private Boolean statusCodigo;

    public RecuperarSenha() {
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public LocalDateTime getExpiracaoCodigo() {
        return expiracaoCodigo;
    }

    public void setExpiracaoCodigo(LocalDateTime expiracaoCodigo) {
        this.expiracaoCodigo = expiracaoCodigo;
    }

    public Boolean getStatusCodigo() {
        return statusCodigo;
    }

    public void setStatusCodigo(Boolean statusCodigo) {
        this.statusCodigo = statusCodigo;
    }

}