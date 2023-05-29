package com.example.miageland.rest;

import com.example.miageland.entities.Attraction;
import com.example.miageland.entities.Employe;
import com.example.miageland.services.EmployeService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/employes")
public class EmployeController {
    @Autowired
    EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }
//    @PostMapping("/ajout")
//    public ResponseEntity<String> ajouterAttraction(@RequestBody Attraction attraction) {
//        attractionService.ajouterAttraction(attraction);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Attraction ajoutée avec succès");
//    }
    //seul le manager peut s'inscrire
    @PostMapping("/signin")
    public ResponseEntity<String> singIn(@RequestBody Employe employe) {
        if (employeService.checkCredentials(employe.getNom(), employe.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Compte déjà crée");
        }
        employeService.singIn(employe);
        return ResponseEntity.ok("Compte crée avec succès ");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String nom, String email) {
        if (employeService.checkCredentials(nom, email)) {
            return ResponseEntity.ok("Connexion réussie");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
    }


    @GetMapping("/role")
    public ResponseEntity<String> isManager(@RequestParam String email) {
        Employe employe=employeService.findByEmail(email);
        if (employe.isManager()) {
            return ResponseEntity.ok("Vous êtes manager !");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous êtes pas manager ! ");
    }
}
