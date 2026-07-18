package com.itb.inf2am.divulgai.dto;

public class ServicoPesquisaDTO {

    private Long servicoId;
    private String servicoNome;
    private String servicoDescricao;
    private Integer servicoContador;
    private String servicoStatus;
    private Long categoriaId;
    private String categoriaNome;
    private Long prestadorId;
    private String prestadorNome;
    private String prestadorCidade;
    private String prestadorUf;
    private String prestadorStatus;
    private Long regiaoId;
    private String regiaoCidade;
    private String regiaoUf;
    private String regiaoZona;

    public ServicoPesquisaDTO(Long servicoId, String servicoNome, String servicoDescricao,
                              Integer servicoContador, String servicoStatus, Long categoriaId,
                              String categoriaNome, Long prestadorId, String prestadorNome,
                              String prestadorCidade, String prestadorUf, String prestadorStatus,
                              Long regiaoId, String regiaoCidade, String regiaoUf, String regiaoZona) {
        this.servicoId = servicoId;
        this.servicoNome = servicoNome;
        this.servicoDescricao = servicoDescricao;
        this.servicoContador = servicoContador;
        this.servicoStatus = servicoStatus;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.prestadorId = prestadorId;
        this.prestadorNome = prestadorNome;
        this.prestadorCidade = prestadorCidade;
        this.prestadorUf = prestadorUf;
        this.prestadorStatus = prestadorStatus;
        this.regiaoId = regiaoId;
        this.regiaoCidade = regiaoCidade;
        this.regiaoUf = regiaoUf;
        this.regiaoZona = regiaoZona;
    }

    public Long getServicoId() { return servicoId; }
    public String getServicoNome() { return servicoNome; }
    public String getServicoDescricao() { return servicoDescricao; }
    public Integer getServicoContador() { return servicoContador; }
    public String getServicoStatus() { return servicoStatus; }
    public Long getCategoriaId() { return categoriaId; }
    public String getCategoriaNome() { return categoriaNome; }
    public Long getPrestadorId() { return prestadorId; }
    public String getPrestadorNome() { return prestadorNome; }
    public String getPrestadorCidade() { return prestadorCidade; }
    public String getPrestadorUf() { return prestadorUf; }
    public String getPrestadorStatus() { return prestadorStatus; }
    public Long getRegiaoId() { return regiaoId; }
    public String getRegiaoCidade() { return regiaoCidade; }
    public String getRegiaoUf() { return regiaoUf; }
    public String getRegiaoZona() { return regiaoZona; }
}
