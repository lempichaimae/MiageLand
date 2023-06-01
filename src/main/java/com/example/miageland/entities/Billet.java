package com.example.miageland.entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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
    private Date date;

    @NotNull
    private double prix;

    //pour vérifier la confirmation/achat

    private boolean estConfirme;

    private boolean estValide;

    @ManyToOne
    private Visiteur visiteur;

    @ManyToOne
    private Employe employe;



}
