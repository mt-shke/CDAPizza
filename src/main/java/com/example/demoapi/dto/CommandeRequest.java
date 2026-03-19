package com.example.demoapi.dto;

import java.util.List;

public class CommandeRequest {

    private Long id_user;
    private List<LigneRequest> lignes;

    public Long getId_user() { return id_user; }
    public void setId_user(Long id_user) { this.id_user = id_user; }

    public List<LigneRequest> getLignes() { return lignes; }
    public void setLignes(List<LigneRequest> lignes) { this.lignes = lignes; }

    public static class LigneRequest {
        private Long id_pizza;
        private Integer quantite;

        public Long getId_pizza() { return id_pizza; }
        public void setId_pizza(Long id_pizza) { this.id_pizza = id_pizza; }

        public Integer getQuantite() { return quantite; }
        public void setQuantite(Integer quantite) { this.quantite = quantite; }
    }
}