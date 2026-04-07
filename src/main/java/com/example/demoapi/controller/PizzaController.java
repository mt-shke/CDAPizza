package com.example.demoapi.controller;
import com.example.demoapi.entity.Pizza;
import com.example.demoapi.security.JwtUtil;
import com.example.demoapi.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {


    private final PizzaService pizzaService;
    private final JwtUtil jwtUtil;

    public PizzaController(PizzaService pizzaService, JwtUtil jwtUtil) {
        this.pizzaService = pizzaService;
        this.jwtUtil = jwtUtil;
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
    public ResponseEntity<?> create(@RequestBody Pizza pizza) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pizzaService.create(pizza));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> update(@PathVariable Long id, @RequestBody Pizza pizza) {
        return pizzaService.findById(id)
                .map(p -> ResponseEntity.ok(pizzaService.update(id, pizza)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        // Lit le token depuis le cookie au lieu du header
        String token = null;
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null || !jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non authentifié");
        }

        String role = jwtUtil.extractRole(token);
        if (!"cuisine".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé");
        }

        return pizzaService.findById(id)
                .map(p -> {
                    pizzaService.delete(id);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}