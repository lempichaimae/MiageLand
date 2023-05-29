package com.example.miageland.rest;

import com.example.miageland.services.BilletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billets")
public class BilletController {

    @Autowired
    BilletService billetService;

    @Autowired
    public BilletController(BilletService billetService) {
        this.billetService = billetService;
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
}
