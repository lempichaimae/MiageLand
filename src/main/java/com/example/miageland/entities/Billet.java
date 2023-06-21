package com.example.miageland.entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Billet {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billet_generator")
    @SequenceGenerator(name = "billet_generator", sequenceName = "billet_seq", allocationSize = 1)
    @Id
    private Long numBillet;

    @NotNull
    private LocalDate date;

    @NotNull
    private double prix;

    private boolean estConfirme;

    private boolean estValide;

    @Enumerated(EnumType.STRING)
    private BilletEtat etat;

    @ManyToOne
    private Visiteur visiteur;



    public Billet(LocalDate date, double prix, boolean estComfirme, boolean estValide, BilletEtat reserve, Visiteur visiteur) {
        this.date = date;
        this.prix = prix;
        this.estConfirme = estComfirme;
        this.estValide = estValide;
        this.etat = reserve;
        this.visiteur= visiteur;
    }
}
