package com.example.demoapi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_commande")
    private Long id_commande;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "etat", nullable = false)
    private String etat;

    @Column(name = "Id_user", nullable = false)
    private Long id_user;

    public Long getId_commande() { return id_commande; }
    public void setId_commande(Long id_commande) { this.id_commande = id_commande; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public Long getId_user() { return id_user; }
    public void setId_user(Long id_user) { this.id_user = id_user; }
}