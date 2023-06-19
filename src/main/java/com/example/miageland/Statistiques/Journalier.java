package com.example.miageland.Statistiques;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Journalier {

    private int nbBilletsVendus;
    private double recette;
    @Override
    public String toString(){
        return "NbBilletsVendus : "+nbBilletsVendus+"\n Recette : "+recette;
    }
}
