package com.example.miageland.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employe_generator")
    @SequenceGenerator(name = "employe_generator", sequenceName = "employe_seq", allocationSize = 1)
    private long id ;

    @NotNull
    private String nom;
    @NotNull
    private String prenom;

    @NotNull
    private String email;

    @NonNull
    @JsonProperty("isManager")
    private boolean isManager;

    @Enumerated(EnumType.STRING)
    private EmployeRole role;

    @OneToMany(mappedBy = "employe")
    private List<Attraction> attractions;
}
