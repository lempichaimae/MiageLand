package com.example.miageland.repositories;

import com.example.miageland.entities.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.Attr;

import java.util.List;

public interface AttractionRepository extends JpaRepository <Attraction, Long> {

    Attraction getAttractionById(Long id);

    Boolean existsAttractionById(Long id);


    List<Attraction> getAttractionByNom(String nom);

    List<Attraction> getAttractionByEstOuverte(boolean etat);


    List<Attraction> deleteAttractionById(Long id);

    List<Attraction> getAllByEstOuverteIsTrue();

    List<Attraction> findAttractionByEstOuverteIsTrue();
}
