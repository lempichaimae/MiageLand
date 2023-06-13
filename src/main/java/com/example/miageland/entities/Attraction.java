package com.example.miageland.entities;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ManyToAny;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attraction {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attraction_generator")
    @SequenceGenerator(name = "attraction_generator", sequenceName = "attraction_seq", allocationSize = 1)
    @Id
    private Long id;

    @NonNull
    private String nom;

    @NonNull
    private boolean estOuverte;

    @ManyToOne
    private Visiteur visiteur;

    @ManyToOne
    private Employe employe;

}
