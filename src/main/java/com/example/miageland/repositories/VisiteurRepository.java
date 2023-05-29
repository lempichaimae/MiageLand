package com.example.miageland.repositories;

import com.example.miageland.entities.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisiteurRepository extends JpaRepository <Visiteur,Long> {

    List<Visiteur> getVisiteurById(Long id);

    List<Visiteur> getVisiteurByNom(String nom);

    List<Visiteur> getVisiteurByEmail(String email);

    List<Visiteur> deleteVisiteurById(Long id);

    List<Visiteur> deleteVisiteurByEmail(String email);

}
