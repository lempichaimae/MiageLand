package com.example.miageland.rest;
import com.example.miageland.Statistiques.Statistiques;
import com.example.miageland.Statistiques.StatistiquesBillets;
import com.example.miageland.Statistiques.StatistiquesVisiteurs;
import com.example.miageland.services.BilletService;
import com.example.miageland.services.StatistiquesService;
import com.example.miageland.services.VisiteurService;
import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("/statistiques")
public class StatistiquesController {

    private StatistiquesService statistiquesService;
    private BilletService billetService;
    private VisiteurService visiteurService;
    @GetMapping("/")
    public Statistiques calculsStats(){
        StatistiquesBillets sb = billetService.getStatistiquesBillet();
        StatistiquesVisiteurs sv = visiteurService.getStatsVisiteurs();
        statistiquesService.setStats(new Statistiques(sb,sv));
        return statistiquesService.getStats();
    }


}
