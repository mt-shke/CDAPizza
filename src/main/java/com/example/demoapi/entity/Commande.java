package com.example.demoapi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_commande;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(nullable = false)
    private String etat;

    @Column(nullable = false)
    private Long id_user;

    public void setId(Long id) {

    }
}