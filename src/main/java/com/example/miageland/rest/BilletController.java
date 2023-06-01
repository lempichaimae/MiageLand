package com.example.miageland.rest;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Employe;
import com.example.miageland.services.BilletService;
import com.example.miageland.services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billets")
public class BilletController {

    @Autowired
    BilletService billetService;
    EmployeService employeService;

    @Autowired
    public BilletController(BilletService billetService, EmployeService employeService) {
        this.billetService = billetService;
        this.employeService = employeService;
    }

    @PostMapping("/validation")
    public ResponseEntity<String> validerBillet(@RequestParam Long numBillet) {
        boolean isValidated = billetService.validateBillet(numBillet);

        if (isValidated) {
            return ResponseEntity.ok("Billet validé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec de validation du billet");
        }
    }

    @PostMapping("/creation")
    public ResponseEntity<String> creerBillets(@RequestBody Billet billet, @RequestParam String email) {
        Employe employe = employeService.findByEmail(email);
        if (employe.isManager()) {
            billetService.creer(billet);
            return ResponseEntity.ok("Billet crée! ");
        } else {
            return ResponseEntity.ok("Oopéaration non réussie");
        }
    }
}
