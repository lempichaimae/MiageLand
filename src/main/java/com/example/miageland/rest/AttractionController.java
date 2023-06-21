package com.example.miageland.rest;

import com.example.miageland.entities.Attraction;
import com.example.miageland.entities.Employe;
import com.example.miageland.entities.EmployeRole;
import com.example.miageland.services.AttractionService;
import com.example.miageland.services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attractions")
public class AttractionController {

    @Autowired
    AttractionService attractionService;
    EmployeService employeService;
    @Autowired
    public AttractionController(AttractionService attractionService, EmployeService employeService) {
        this.attractionService = attractionService;
        this.employeService = employeService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllAttractions() {
        List<Attraction> attractions = attractionService.getAllAttractions();

        List<Map<String, Object>> attractionInfos = new ArrayList<>();
        for (Attraction attraction : attractions) {
            Map<String, Object> info = new HashMap<>();
            info.put("id", attraction.getId());
            info.put("nom", attraction.getNom());
            info.put("Ouverte", attraction.isEstOuverte());
            attractionInfos.add(info);
        }

        return ResponseEntity.ok(attractionInfos);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> ajouterAttraction(@RequestBody Attraction attraction ,@RequestParam String email) {
        if(employeService.findByEmail(email).isManager())
        {
            attractionService.ajouterAttraction(attraction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Attraction ajoutée avec succès");
        }
        else if (employeService.isAdmin(email) == EmployeRole.valueOf("Administrateur"))
        {
            attractionService.ajouterAttraction(attraction);
            return ResponseEntity.ok("Attraction ajoutée avec succès");
        }

        else {
            return ResponseEntity.status(HttpStatus.CREATED).body("Vous pouvez pas réaliser cette opération !");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> supprimerAttraction(@RequestParam Long id, @RequestParam String email) {
        if(employeService.findByEmail(email).isManager())
        {
            attractionService.supprimerAttraction(id);
            return ResponseEntity.ok("Attraction supprimée avec succès");

        }
        else if (employeService.isAdmin(email) == EmployeRole.valueOf("Administrateur"))
        {
            attractionService.supprimerAttraction(id);
            return ResponseEntity.ok("Attraction supprimée avec succès");
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Vous pouvez pas réaliser cette opération !");
        }
    }

    @PostMapping("/fermer")
    public ResponseEntity<String> fermerAttraction(@RequestParam Long id,@RequestParam String email) {
        if(employeService.findByEmail(email).isManager())
        {
            attractionService.fermerAttraction(id);
            return ResponseEntity.ok("Attraction fermée avec succès");
        }
        else if (employeService.isAdmin(email) == EmployeRole.valueOf("Administrateur"))
        {
            attractionService.fermerAttraction(id);
            return ResponseEntity.ok("Attraction fermée avec succès");
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Vous pouvez pas réaliser cette opération !");
        }
    }

    @PostMapping("/rouvrir")
    public ResponseEntity<String> rouvrirAttraction(@RequestParam Long id, @RequestParam String email) {
        if(employeService.findByEmail(email).isManager())
        {
            attractionService.rouvrirAttraction(id);
            return ResponseEntity.ok("Attraction rouverte avec succès");
        }
        else if (employeService.isAdmin(email) == EmployeRole.valueOf("Administrateur"))
        {
            attractionService.rouvrirAttraction(id);
            return ResponseEntity.ok("Attraction rouverte avec succès");
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Vous pouvez pas réaliser cette opération !");
        }
    }


}
