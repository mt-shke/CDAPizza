package com.example.demoapi.service;

import com.example.demoapi.entity.Contenir;
import com.example.demoapi.entity.ContenirId;
import com.example.demoapi.repository.ContenirRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenirService {

    private final ContenirRepository contenirRepository;

    public ContenirService(ContenirRepository contenirRepository) {
        this.contenirRepository = contenirRepository;
    }

    public List<Contenir> findAll() {
        return contenirRepository.findAll();
    }

    public List<Contenir> findByCommande(Long id_commande) {
        return contenirRepository.findByIdIdCommande(id_commande);
    }

    public Optional<Contenir> findById(ContenirId id) {
        return contenirRepository.findById(id);
    }

    public Contenir create(Contenir contenir) {
        return contenirRepository.save(contenir);
    }

    public Contenir update(ContenirId id, Contenir contenir) {
        return contenirRepository.findById(id)
                .map(existing -> {
                    existing.setQuantite(contenir.getQuantite());
                    return contenirRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Ligne non trouvée"));
    }

    public void delete(ContenirId id) {
        contenirRepository.deleteById(id);
    }
}