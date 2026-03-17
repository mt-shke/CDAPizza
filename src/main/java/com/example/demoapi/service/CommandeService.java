package com.example.demoapi.service;

import com.example.demoapi.entity.Commande;
import com.example.demoapi.entity.Tache;
import com.example.demoapi.repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public List<Commande> findAll(){
        return commandeRepository.findAll();
    }

    public Optional<Commande> findById(Long id) {
        return commandeRepository.findById(id);
    }

    public Commande create(Commande commande) {
        return commandeRepository.save(commande);
    }

    public Commande update(Long id, Commande commande) {
        commande.setId(id);
        return commandeRepository.save(commande);
    }

    public void delete(Long id) {
        commandeRepository.deleteById(id);
    }
}
