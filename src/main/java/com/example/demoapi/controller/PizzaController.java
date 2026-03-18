package com.example.demoapi.controller;

import com.example.demoapi.entity.Pizza;
import com.example.demoapi.service.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<List<Pizza>> findAll() {
        return ResponseEntity.ok(pizzaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> findById(@PathVariable Long id) {
        return pizzaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pizza> create(@RequestBody Pizza pizza) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pizzaService.create(pizza));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> update(@PathVariable Long id, @RequestBody Pizza pizza) {
        return pizzaService.findById(id)
                .map(p -> ResponseEntity.ok(pizzaService.update(id, pizza)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return pizzaService.findById(id)
                .map(p -> {
                    pizzaService.delete(id);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}