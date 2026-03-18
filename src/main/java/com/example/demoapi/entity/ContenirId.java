package com.example.demoapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContenirId implements Serializable {

    @Column(name = "Id_commande")
    private Long idCommande;

    @Column(name = "Id_pizza")
    private Long idPizza;

    public ContenirId() {}

    public ContenirId(Long idCommande, Long idPizza) {
        this.idCommande = idCommande;
        this.idPizza = idPizza;
    }

    public Long getIdCommande() { return idCommande; }
    public void setIdCommande(Long idCommande) { this.idCommande = idCommande; }

    public Long getIdPizza() { return idPizza; }
    public void setIdPizza(Long idPizza) { this.idPizza = idPizza; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContenirId)) return false;
        ContenirId that = (ContenirId) o;
        return Objects.equals(idCommande, that.idCommande) &&
                Objects.equals(idPizza, that.idPizza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, idPizza);
    }
}