package com.example.miageland.rest;

import com.example.miageland.entities.Attraction;
import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Employe;
import com.example.miageland.services.AttractionService;
import com.example.miageland.services.BilletService;
import com.example.miageland.services.EmployeService;
import com.example.miageland.services.VisiteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/billets")
public class BilletController {

    BilletService billetService;
    VisiteurService visiteurService;

    @Autowired
    public BilletController(BilletService billetService, VisiteurService visiteurService) {
        this.billetService = billetService;
        this.visiteurService = visiteurService;
    }

    /**
     * un employé valide son billet
     * @param numBillet
     * @return
     */
    @PostMapping("/validation")
    public ResponseEntity<String> validerBillet(@RequestParam Long numBillet) {
        boolean isValidated = billetService.validateBillet(numBillet);

        if (isValidated) {
            return ResponseEntity.ok("Billet validé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec de validation du billet");
        }
    }

    /**
     * un visiteur annule son billet
     * @param numBillet
     * @return
     */
    @PostMapping("/annulation")
    public ResponseEntity<String> annulerBillet(@RequestParam Long numBillet, @RequestParam Long id) {
        try {
            Billet billet = billetService.annulerBillet(numBillet, id);
            return ResponseEntity.ok("Billet annulé avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * un visiteur réserve un billet
     * @param date
     * @param id
     * @return
     */
    @PostMapping("/reserverBillet")
    public ResponseEntity<Map<String, Object>> reserverBillet(@RequestParam LocalDate date, @RequestParam Long id) {
        Billet billet = billetService.reserverBillet(id, date);

        Map<String, Object> response = new HashMap<>();
        response.put("numBillet", billet.getNumBillet());
        response.put("prix", billet.getPrix());
        response.put("date", billet.getDate());

        return ResponseEntity.ok(response);
    }

    /**
     * un visiteur achète un billet
     */
    @PostMapping("/payerBillet")
    public ResponseEntity<Map<String, Object>> payerBillet(@RequestParam Long numBillet) {
        Billet billet = billetService.payerBillet(numBillet);

        Map<String, Object> response = new HashMap<>();
        response.put("numBillet", billet.getNumBillet());
        response.put("prix", billet.getPrix());
        response.put("date", billet.getDate());

        return ResponseEntity.ok(response);
    }

    /**
     * un visiteur récupère ses billets
     * @param id
     * @return
     */
    @GetMapping("/visiteur")
    public ResponseEntity<List<Map<String, Object>>> getBilletsByVisiteur(@RequestParam Long id) {
        List<Billet> billets = billetService.getBilletsByVisiteur(id);
        List<Map<String, Object>> billetsInfo = new ArrayList<>();

        for (Billet billet : billets) {
            Map<String, Object> billetInfo = new HashMap<>();
            billetInfo.put("numBillet", billet.getNumBillet());
            billetInfo.put("prix", billet.getPrix());
            billetInfo.put("date", billet.getDate());
            billetsInfo.add(billetInfo);
        }

        return ResponseEntity.ok(billetsInfo);
    }

    /**
     * un employé récupère les billets reserves d'un visiteur
     * @param id
     * @return
     */
    @GetMapping("/billetsReserves")
    public ResponseEntity<List<Map<String, Object>>> getBilletsReservesByVisiteur(@RequestParam Long id) {
        List<Billet> billets = billetService.getBilletsReservesByVisiteur(id);
        List<Map<String, Object>> billetsInfo = new ArrayList<>();

        for (Billet billet : billets) {
            Map<String, Object> billetInfo = new HashMap<>();
            billetInfo.put("numBillet", billet.getNumBillet());
            billetInfo.put("date", billet.getDate());
            billetsInfo.add(billetInfo);
        }

        return ResponseEntity.ok(billetsInfo);
    }

    /**
     * un employé récupère les billets payés d'un visiteur
     * @param id
     * @return
     */

    @GetMapping("/billetsPayes")
    public ResponseEntity<List<Map<String, Object>>> getBilletsPayesByVisiteur(@RequestParam Long id) {
        List<Billet> billets = billetService.getBilletsPayesByVisiteur(id);
        List<Map<String, Object>> billetsInfo = new ArrayList<>();

        for (Billet billet : billets) {
            Map<String, Object> billetInfo = new HashMap<>();
            billetInfo.put("numBillet", billet.getNumBillet());
            billetInfo.put("date", billet.getDate());
            billetInfo.put("prix", billet.getDate());
            billetsInfo.add(billetInfo);
        }

        return ResponseEntity.ok(billetsInfo);
    }



}
