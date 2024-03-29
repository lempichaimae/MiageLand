package com.example.miageland.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visiteur {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visitor_generator")
    @SequenceGenerator(name = "visitor_generator", sequenceName = "visitor_seq", allocationSize = 1)
    private long id ;

    @NotNull
    private String nom;
    @NotNull
    private String prenom;

    @NotNull
    private String email;

    //visietur peut acheter plusieurs tickets
    @OneToMany(mappedBy = "visiteur")
    private List<Billet> billets;

    //visietur peut s'inscire à plusieurs attractions
    @OneToMany(mappedBy = "visiteur")
    private List<Attraction> attractions;




}
