package com.example.miageland.rest;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Parc;
import com.example.miageland.repositories.BilletRepository;
import com.example.miageland.repositories.ParcRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parc")
@AllArgsConstructor
public class ParcController {
    private BilletRepository billetRepository;
    private ParcRepository parcRepository;
    /**
     * Retourne un objet Parc ayant une jauge qui avec verification de jauge
     * @param
     * @return Parc
     */
    @PatchMapping("/jauge")
    public Parc setJauge(@RequestBody Map<String, String> body) throws Exception{
        int jauge = Integer.parseInt(body.get("jauge"));
        LocalDate date = LocalDate.now();
        int max= billetRepository.getMaxInFutureDates(date);
        if (jauge < max) {
            throw new Exception("Jauge must be at least " + max);
        }
        Parc parc = parcRepository.findById(1L).orElseThrow();
        parc.setJauge(jauge);
        parcRepository.save(parc);
        return parc;
    }
}
