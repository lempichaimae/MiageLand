package com.example.miageland.services;

import com.example.miageland.entities.Attraction;
import com.example.miageland.repositories.AttractionRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionService {

    private AttractionRepository attractionRepository;

    @Autowired
    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }



    public void ajouterAttraction(Attraction attraction) {
        // Vérifier si l'attraction existe déjà
        @NonNull
        String nom = attraction.getNom();
        if (!attractionRepository.getAttractionByNom(nom).isEmpty()) {
            throw new IllegalArgumentException("L'attraction existe déjà");
        }

        attractionRepository.save(attraction);
    }

    @Transactional
    public void supprimerAttraction(Long id) {
        // Vérifier si l'attraction existe déjà
        if (attractionRepository.existsAttractionById(id)) {
            attractionRepository.deleteAttractionById(id);
        } else {
            throw new IllegalArgumentException("L'attraction n'existe pas");
        }
    }

    public void fermerAttraction(Long id) {
        if (!attractionRepository.existsAttractionById(id)) {
            throw new IllegalArgumentException("L'attraction n'existe pas");
        }

        Attraction attraction = attractionRepository.getAttractionById(id);
        if (attraction.isEstOuverte()) {
            attraction.setEstOuverte(false);
            attractionRepository.save(attraction);
        } else {
            throw new IllegalArgumentException("L'attraction est déjà fermée");
        }
    }

    public void rouvrirAttraction(Long id) {
        if (!attractionRepository.existsAttractionById(id)) {
            throw new IllegalArgumentException("L'attraction n'existe pas");
        }

        Attraction attraction = attractionRepository.getAttractionById(id);
        if (!attraction.isEstOuverte()) {
            attraction.setEstOuverte(true);
            attractionRepository.save(attraction);
        } else {
            throw new IllegalArgumentException("L'attraction est déjà ouverte");
        }
    }

    public List<Attraction> getAllAttractions() {

        if (attractionRepository.findAll().isEmpty())
        {
            throw new IllegalArgumentException("Aucune attraction");
        }
        return  attractionRepository.findAll();
    }

    public List<Attraction> attractionOuverte(){

        if(attractionRepository.getAllByEstOuverteIsTrue().isEmpty()){
            throw new IllegalArgumentException("Malheureusement aucune attraction est ouverte !");
        }
        return attractionRepository.getAllByEstOuverteIsTrue();
    }
}
