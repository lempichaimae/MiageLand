package com.example.miageland.rest;

import com.example.miageland.entities.Attraction;
import com.example.miageland.entities.AttractionOuverte;
import com.example.miageland.entities.Visiteur;
import com.example.miageland.services.AttractionService;
import com.example.miageland.services.BilletService;
import com.example.miageland.services.VisiteurService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/visiteurs")
public class VisiteurController {

    @Autowired
    VisiteurService visiteurService;
    AttractionService attractionService;
    BilletService billetService;

    public VisiteurController(VisiteurService visiteurService, AttractionService attractionService, BilletService billetService){
        this.visiteurService=visiteurService;
        this.attractionService=attractionService;
        this.billetService=billetService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> singIn(@RequestBody Visiteur visiteur) {
        if (visiteurService.checkCredentials(visiteur.getNom(), visiteur.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Compte déjà crée");
        }
        visiteurService.singIn(visiteur);
        return ResponseEntity.status(HttpStatus.OK).body("Compte crée !");
    }

    @GetMapping("/connexion")
    public ResponseEntity<String> login(@RequestParam String nom, String email) {
        if (visiteurService.checkCredentials(nom, email)) {
            return ResponseEntity.ok("Connexion réussie");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
    }

    @Transactional
    @DeleteMapping("/supprimer")
    public ResponseEntity<String> suppCompte(@RequestParam String email) {
        Visiteur visiteur = visiteurService.findByEmail(email);
        if (visiteur != null ) {
            visiteurService.supprimerCompte(email);
            return ResponseEntity.status(HttpStatus.OK).body("Compte supprimé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Suppression non effectuée");
        }
    }

    @GetMapping("/allAttractions")
    public ResponseEntity<List<AttractionOuverte>> attractionsOuvertes(){

        List<Attraction> attractions = attractionService.attractionOuverte();

        List<AttractionOuverte> attractionsOuv = new ArrayList<>();
        for (Attraction attraction : attractions) {
            AttractionOuverte attractionDTO = new AttractionOuverte();
            attractionDTO.setId(attraction.getId());
            attractionDTO.setNom(attraction.getNom());
            attractionsOuv.add(attractionDTO);
        }

        return ResponseEntity.ok(attractionsOuv);
    }

    @GetMapping("/showVisiteur")
    public Visiteur showVisiteurDetails(@RequestParam Long id) {

        Visiteur visiteur = visiteurService.Visiteur(id);

        return visiteur;
    }





}
