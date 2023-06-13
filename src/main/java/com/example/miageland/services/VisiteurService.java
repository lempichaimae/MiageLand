package com.example.miageland.services;

import com.example.miageland.entities.Visiteur;
import com.example.miageland.repositories.VisiteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisiteurService {

    @Autowired
    VisiteurRepository visiteurRepository;

    public VisiteurService(VisiteurRepository visiteurRepository)
    {
        this.visiteurRepository=visiteurRepository;
    }

    public Visiteur findByEmail(String email){
        if(visiteurRepository.getVisiteurByEmail(email) != null) {
            return visiteurRepository.getVisiteurByEmail(email);
        }
        throw new IllegalArgumentException("User not found !");
    }

    public boolean checkCredentials(String nom, String email) {

        if (visiteurRepository.getVisiteurByEmailAndAndNom(email, nom) != null) {
            return true;
        }
        return false;
    }

    public void singIn (Visiteur visiteur) {
        Visiteur v = visiteurRepository.getVisiteurByEmailAndAndNom(visiteur.getNom(), visiteur.getEmail());
        if (v != null) {
            throw new IllegalArgumentException("Vous êtes déjà inscrit !");
        } else {
                visiteurRepository.save(visiteur);
        }
        }

    public void supprimerCompte(String email){
        if (visiteurRepository.existsVisiteurByEmail(email)) {
            visiteurRepository.deleteVisiteurByEmail(email);
        } else {
            throw new IllegalArgumentException("Ce visiteur n'existe pas");
        }
    }

    public Visiteur Visiteur(Long id){
        if(visiteurRepository.existsById(id)){
            Visiteur visiteur = visiteurRepository.getVisiteurById(id);
            return visiteur;
        } else {
            throw new IllegalArgumentException("Ce visiteur n'existe pas");
        }
    }

    }



