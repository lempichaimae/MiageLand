package com.example.miageland.rest;

import com.example.miageland.entities.Attraction;
import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Employe;
import com.example.miageland.services.BilletService;
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
    BilletService billetService;

    public EmployeController(EmployeService employeService, BilletService billetService) {
        this.employeService = employeService;
        this.billetService = billetService;
    }

    /**seul le manager peut s'inscrire
     *
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<String> singIn(@RequestBody Employe employe) {
        if (employeService.checkCredentials(employe.getNom(), employe.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Compte déjà crée");
        }
        employe.setManager(true);
        employeService.singIn(employe);
        return ResponseEntity.ok("Compte crée avec succès ");
    }

    @GetMapping ("/login")
    public ResponseEntity<String> login(@RequestParam String nom, String email) {
        if (employeService.checkCredentials(nom, email)) {
            return ResponseEntity.ok("Connexion réussie");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
    }

    @PostMapping("/ajoutEmploye")
    public ResponseEntity<String> ajoutEmploye(@RequestBody Employe employe,@RequestParam String email) {
        Employe manager = employeService.findByEmail(email);
        if (manager != null && manager.isManager()) {
            employe.setManager(false);
            employeService.ajouterEmploye(employe);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employé ajouté avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autorisation refusée");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> suppEmploye(@RequestParam Long id, @RequestParam String email) {
        Employe manager = employeService.findByEmail(email);
        if (manager != null && manager.isManager()) {
            employeService.supprimerEmploye(id);
            return ResponseEntity.status(HttpStatus.OK).body("Employé supprimé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autorisation refusée");
        }
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

