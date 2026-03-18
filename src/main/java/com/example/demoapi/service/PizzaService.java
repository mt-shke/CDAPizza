package com.example.demoapi.service;

import com.example.demoapi.entity.Pizza;
import com.example.demoapi.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public Optional<Pizza> findById(Long id) {
        return pizzaRepository.findById(id);
    }

    public Pizza create(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Long id, Pizza pizza) {
        return pizzaRepository.findById(id)
                .map(existing -> {
                    existing.setNom(pizza.getNom());
                    existing.setPrix(pizza.getPrix());
                    return pizzaRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Pizza non trouvée : " + id));
    }

    public void delete(Long id) {
        pizzaRepository.deleteById(id);
    }
}