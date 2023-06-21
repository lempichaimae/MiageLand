package com.example.miageland.Statistiques;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class StatistiquesVisiteurs {
    private int nbVisiteurs;
    private List<Detail> detail;
}
