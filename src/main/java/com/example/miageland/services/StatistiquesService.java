package com.example.miageland.services;

import com.example.miageland.Statistiques.Statistiques;
import com.example.miageland.Statistiques.StatistiquesBillets;
import com.example.miageland.entities.Visiteur;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
public class StatistiquesService {
    private Statistiques stats;
    private BilletService billetService;
    private VisiteurService visiteurService;



}
