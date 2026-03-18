package com.example.demoapi.controller;

import com.example.demoapi.entity.Contenir;
import com.example.demoapi.entity.ContenirId;
import com.example.demoapi.service.ContenirService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenir")
public class ContenirController {

    private final ContenirService contenirService;

    public ContenirController(ContenirService contenirService) {
        this.contenirService = contenirService;
    }

    @GetMapping
    public ResponseEntity<List<Contenir>> findAll() {
        return ResponseEntity.ok(contenirService.findAll());
    }

    @GetMapping("/commande/{id_commande}")
    public ResponseEntity<List<Contenir>> findByCommande(@PathVariable Long id_commande) {
        return ResponseEntity.ok(contenirService.findByCommande(id_commande));
    }

    @PostMapping
    public ResponseEntity<Contenir> create(@RequestBody Contenir contenir) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contenirService.create(contenir));
    }

    @PutMapping("/{idCommande}/{idPizza}")
    public ResponseEntity<Contenir> update(
            @PathVariable Long idCommande,
            @PathVariable Long idPizza,
            @RequestBody Contenir contenir) {
        ContenirId id = new ContenirId(idCommande, idPizza);
        return contenirService.findById(id)
                .map(c -> ResponseEntity.ok(contenirService.update(id, contenir)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idCommande}/{idPizza}")
    public ResponseEntity<Object> delete(
            @PathVariable Long idCommande,
            @PathVariable Long idPizza) {
        ContenirId id = new ContenirId(idCommande, idPizza);
        return contenirService.findById(id)
                .map(c -> {
                    contenirService.delete(id);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}