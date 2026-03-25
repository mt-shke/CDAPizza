package com.example.demoapi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_pizza")
    private Long id_pizza;

    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    public Long getId_pizza() { return id_pizza; }
    public void setId_pizza(Long id_pizza) { this.id_pizza = id_pizza; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
}