package com.itb.inf2am.divulgai.model.entity;

import jakarta.persistence.*;

@Entity
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String tipoContato;

    @Column(length = 255, nullable = false)
    private String link;

    @Column(length = 20, nullable = false)
    private String statusContato;

    // Substitui o ManyToOne: só guarda o id do prestador
        @Column(name = "prestador_id", nullable = false)
        private Long prestadorId;
    
    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoContato() { return tipoContato; }
    public void setTipoContato(String tipoContato) { this.tipoContato = tipoContato; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getStatusContato() { return statusContato; }
    public void setStatusContato(String statusContato) { this.statusContato = statusContato; }

    public Long getPrestadorId() { return prestadorId; }
    public void setPrestadorId(Long prestadorId) { this.prestadorId = prestadorId; }
}
