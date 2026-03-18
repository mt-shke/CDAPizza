package com.example.demoapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contenir")
public class Contenir {

    @EmbeddedId
    private ContenirId id;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public ContenirId getId() { return id; }
    public void setId(ContenirId id) { this.id = id; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
}