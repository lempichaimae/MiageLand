package com.example.miageland.Statistiques;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Detail {
    private String nomVisiteur;
    private String prenomVisiteur;
    private int nbBilletsTotal;
    private int nbVisites;
}
