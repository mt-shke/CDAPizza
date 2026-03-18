package com.example.demoapi.repository;

import com.example.demoapi.entity.Contenir;
import com.example.demoapi.entity.ContenirId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenirRepository extends JpaRepository<Contenir, ContenirId> {
    List<Contenir> findByIdIdCommande(Long idCommande);
}