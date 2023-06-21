package com.example.miageland.Statistiques;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
public class StatistiquesBillets {
    private int nbBilletsVendus;
    private int nbBilletsReserves;

    private double recette;

    private HashMap<LocalDate,Journalier> journalier;

}
