package com.itb.inf2am.divulgai.dto;

import com.itb.inf2am.divulgai.model.entity.Servico;
import java.util.Base64;

public class ServicoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String foto; // BASE64 STRING
    private Integer contador;

    private Long prestadorId;
    private Long categoriaId;

    // ✅ Obrigatório para Jackson
    public ServicoDTO() {
    }

    // ✔ Entity -> DTO
    public ServicoDTO(Servico servicoSalvo) {
        if (servicoSalvo != null) {
            this.id = servicoSalvo.getId();
            this.nome = servicoSalvo.getNome();
            this.descricao = servicoSalvo.getDescricao();
            this.contador = servicoSalvo.getContador();

            this.foto = servicoSalvo.getFoto() != null
                    ? Base64.getEncoder().encodeToString(servicoSalvo.getFoto())
                    : null;

            this.prestadorId = servicoSalvo.getPrestador() != null
                    ? servicoSalvo.getPrestador().getId()
                    : null;

            this.categoriaId = servicoSalvo.getCategoria() != null
                    ? servicoSalvo.getCategoria().getId()
                    : null;
        }
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public Long getPrestadorId() {
        return prestadorId;
    }

    public void setPrestadorId(Long prestadorId) {
        this.prestadorId = prestadorId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}