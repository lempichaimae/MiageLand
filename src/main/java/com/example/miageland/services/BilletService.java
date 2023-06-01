package com.example.miageland.services;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Visiteur;
import com.example.miageland.repositories.BilletRepository;
import com.example.miageland.repositories.VisiteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BilletService {

    private BilletRepository billetRepository;
    private VisiteurRepository visiteurRepository;

    @Autowired
    public BilletService(BilletRepository billetRepository, VisiteurRepository visiteurRepository) {
        this.billetRepository = billetRepository;
        this.visiteurRepository = visiteurRepository;
    }

    public void reserverBillet(Long id, Date date) {
        // Récupérer le visiteur à partir de l'ID
        Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visiteur introuvable"));

        // Créer un nouveau billet
        Billet billet = new Billet();
        billet.setDate(date);
        billet.setVisiteur(visiteur);
        billet.setEstConfirme(true);
        billet.setEstValide(false);

        // Enregistrer le billet dans la base de données
        billetRepository.save(billet);

        // Ajouter le billet à la liste de billets du visiteur
        visiteur.getBillets().add(billet);
        visiteurRepository.save(visiteur);
    }

    public boolean validateBillet(Long numBillet) {
        Billet billet = billetRepository.getBilletByNumBillet(numBillet);

        if (billet == null) {
            // Le billet n'existe pas
            return false;
        }
        else {
            if (!billet.isEstConfirme()) {
                // Le billet a été annulé
                return false;
            }
            else {
                if (billet.isEstValide()) {
                    // Le billet a déjà été validé/utilisé
                    return false;
                }
                else
                // Marquer le billet comme utilisé/validé
                billet.setEstValide(true);
                billetRepository.save(billet);
                return true;
            }
        }
    }

    public Billet creer(Billet billet){
        billetRepository.save(billet);
        billet.setEstValide(false);
        billet.setEstConfirme(false);
        return billet;
    }

    public List<Billet> allBillet(Date date){
       List<Billet> billets= billetRepository.getBilletByDate(date);
       if(billets.isEmpty()) {
           throw new IllegalArgumentException(" Pas de billet ");
       }
       else {
           return billets;
       }
    }

}
