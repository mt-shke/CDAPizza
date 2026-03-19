package com.example.demoapi.service;

import com.example.demoapi.dto.CommandeRequest;
import com.example.demoapi.entity.Commande;
import com.example.demoapi.entity.Contenir;
import com.example.demoapi.entity.ContenirId;
import com.example.demoapi.repository.CommandeRepository;
import com.example.demoapi.repository.ContenirRepository;
import com.example.demoapi.repository.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ContenirRepository contenirRepository;
    private final PizzaRepository pizzaRepository;

    public CommandeService(CommandeRepository commandeRepository,
                           ContenirRepository contenirRepository,
                           PizzaRepository pizzaRepository) {
        this.commandeRepository = commandeRepository;
        this.contenirRepository = contenirRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> findById(Long id) {
        return commandeRepository.findById(id);
    }

    @Transactional
    public Commande createAvecLignes(CommandeRequest request) {

        // 1 — Calcul du montant brut et total de pizzas
        BigDecimal montantBrut = BigDecimal.ZERO;
        int totalPizzas = 0;

        for (CommandeRequest.LigneRequest ligne : request.getLignes()) {
            var pizza = pizzaRepository.findById(ligne.getId_pizza())
                    .orElseThrow(() -> new RuntimeException("Pizza non trouvée : " + ligne.getId_pizza()));
            montantBrut = montantBrut.add(
                    pizza.getPrix().multiply(BigDecimal.valueOf(ligne.getQuantite()))
            );
            totalPizzas += ligne.getQuantite();
        }

        // 2 — Calcul des remises
        BigDecimal remise = BigDecimal.ZERO;

        // Remise 10% toutes les 3 commandes
        long nbCommandes = commandeRepository.findByIdUser(request.getId_user()).size();
        if ((nbCommandes + 1) % 3 == 0) {
            remise = remise.add(montantBrut.multiply(BigDecimal.valueOf(0.10)));
        }

        // Remise 5% si plus de 5 pizzas
        if (totalPizzas >= 5) {
            remise = remise.add(montantBrut.multiply(BigDecimal.valueOf(0.05)));
        }

        BigDecimal montantFinal = montantBrut.subtract(remise).setScale(2, RoundingMode.HALF_UP);

        // 3 — Créer la commande
        Commande commande = new Commande();
        commande.setDate(LocalDateTime.now());
        commande.setMontant(montantFinal);
        commande.setEtat("PAYER");
        commande.setId_user(request.getId_user());
        Commande commandeSauvee = commandeRepository.save(commande);

        // 4 — Créer les lignes
        for (CommandeRequest.LigneRequest ligne : request.getLignes()) {
            Contenir contenir = new Contenir();
            ContenirId id = new ContenirId(commandeSauvee.getId_commande(), ligne.getId_pizza());
            contenir.setId(id);
            contenir.setQuantite(ligne.getQuantite());
            contenirRepository.save(contenir);
        }

        return commandeSauvee;
    }

    public Commande update(Long id, Commande commande) {
        return commandeRepository.findById(id)
                .map(existing -> {
                    existing.setDate(commande.getDate());
                    existing.setMontant(commande.getMontant());
                    existing.setEtat(commande.getEtat());
                    existing.setId_user(commande.getId_user());
                    return commandeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Commande non trouvée : " + id));
    }

    public void delete(Long id) {
        commandeRepository.deleteById(id);
    }
}