package com.example.miageland.repositories;

import com.example.miageland.entities.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisiteurRepository extends JpaRepository <Visiteur,Long> {

   Visiteur getVisiteurById(Long id);

   Boolean existsVisiteurByEmail(String email);

    Visiteur getVisiteurByNom(String nom);

    Visiteur getVisiteurByEmail(String email);

    Visiteur deleteVisiteurById(Long id);

    Integer deleteVisiteurByEmail(String email);

    Visiteur getVisiteurByEmailAndAndNom(String nom, String email);

    @Query("select count(id) from Visiteur")
    int countAll();
}
