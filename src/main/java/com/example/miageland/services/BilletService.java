package com.example.miageland.services;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Visiteur;
import com.example.miageland.repositories.BilletRepository;
import com.example.miageland.repositories.VisiteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class BilletService {

    private BilletRepository billetRepository;

    private VisiteurRepository visiteurRepository;

    @Autowired
    public BilletService(BilletRepository billetRepository, VisiteurRepository visiteurRepository) {
        this.billetRepository = billetRepository;
        this.visiteurRepository = visiteurRepository;
    }

    private double genererPrixAleatoire() {
        Random random = new Random();
        double minPrix = 20.0; // Prix minimum
        double maxPrix = 70.0; // Prix maximum

        // Générer un prix aléatoire entre minPrix et maxPrix
        double prix = minPrix + (maxPrix - minPrix) * random.nextDouble();

        // Arrondir le prix à deux décimales
        prix = Math.round(prix * 100.0) / 100.0;

        return prix;
    }

    public Billet reserverBillet(Long id, Date date) {
        // Récupérer le visiteur à partir de l'ID
        Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visiteur introuvable"));

        // Créer un nouveau billet
        Billet billet = new Billet();
        billet.setDate(date);
        billet.setPrix(genererPrixAleatoire());
        billet.setVisiteur(visiteur);
        billet.setEstConfirme(true);
        billet.setEstValide(false);

        // Enregistrer le billet dans la base de données
        billetRepository.save(billet);

        // Ajouter le billet à la liste de billets du visiteur
        visiteur.getBillets().add(billet);
        visiteurRepository.save(visiteur);

        return billet;
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

    //un visiteur récupère ses billets
    public List<Billet> getBilletsByVisiteur(Long id){
        Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visiteur introuvable"));
        return visiteur.getBillets();
    }

    /**
     * Un visiteur annule un billet
     * @param id
     * @param numBillet
     * @return
     */
    public Billet annulerBillet( Long numBillet, Long id){
        Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visiteur introuvable"));
        Billet billet = billetRepository.getBilletByNumBillet(numBillet);

        if (billet == null) {
            throw new IllegalArgumentException("Billet introuvable");
        } else {
            if (billet.getVisiteur().getId() != id) {
                throw new IllegalArgumentException("Billet ne correspond pas au visiteur");
            } else if (billet.isEstValide()) {
                throw new IllegalArgumentException("Billet déjà utilisé");
            } else if (billet.isEstConfirme()) {
                billet.setEstConfirme(false);
                billetRepository.save(billet);
                return billet;
            } else {
                throw new IllegalArgumentException("Billet déjà annulé");
            }
        }
    }

}
