package com.example.miageland.services;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.BilletEtat;
import com.example.miageland.entities.Visiteur;
import com.example.miageland.repositories.BilletRepository;
import com.example.miageland.repositories.VisiteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /**
     * Générer un prix aléatoire entre 20 et 70 euros pour le billet
     * @return
     */
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

    /**
     * Réserver un billet par un visiteur
     * le prix est initialisé à 0 pour une réservation non payée
     * @param id
     * @param date
     * @return
     */
    public Billet reserverBillet(Long id, LocalDate date) {
        // Récupérer le visiteur à partir de l'ID
        Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visiteur introuvable"));

        // Créer un nouveau billet
        Billet billet = new Billet();
        billet.setDate(date);
        billet.setPrix(0.0); // Prix initialisé à 0 pour une réservation non payée
        billet.setVisiteur(visiteur);
        //non cofirmé tant qu'il est pas payé
        billet.setEstConfirme(false);
        billet.setEstValide(false);
        billet.setEtat(BilletEtat.RESREVE); // Billet réservé, mais non payé

        // Enregistrer le billet dans la base de données
        billetRepository.save(billet);

        // Ajouter le billet à la liste de billets du visiteur
        visiteur.getBillets().add(billet);
        visiteurRepository.save(visiteur);

        return billet;
    }

    /**
     * Payer un billet
     * @param numBillet
     * @return
     */
    public Billet payerBillet(Long numBillet) {
        Billet billet = billetRepository.getBilletByNumBillet(numBillet);
        //verifier si le billet existe et si il est déja payé
        if (billet == null || billet.isEstConfirme()) {
            throw new IllegalArgumentException("Billet introuvable ou déjà payé");
        }
        else {
            billet.setPrix(genererPrixAleatoire());
            billet.setEstConfirme(true);
            billet.setEtat(BilletEtat.PAYE); // Billet payé
            billetRepository.save(billet);
            return billet;
        }
    }


    /**
     * Valider un billet
     * @param numBillet
     * @return
     */
    public boolean validateBillet(Long numBillet) {
        Billet billet = billetRepository.getBilletByNumBillet(numBillet);

        if (billet == null) {
            // Le billet n'existe pas
            return false;
        }
        else {
            if (!billet.isEstConfirme()) {
                // Le billet a été annulé ou non payé
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

    /**
     * Récupérer un billet à partir de son numéro
     * @param id
     * @return
     */
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
        Visiteur visiteur = visiteurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visiteur introuvable"));

        Billet billet = billetRepository.getBilletByNumBillet(numBillet);

        if (billet == null) {
            throw new IllegalArgumentException("Billet introuvable");
        } else if (billet.getVisiteur().getId() != id) {
            throw new IllegalArgumentException("Billet ne correspond pas au visiteur");
        } else if (billet.isEstValide()) {
            throw new IllegalArgumentException("Billet déjà utilisé");
        } else if (!billet.isEstConfirme()) {
            throw new IllegalArgumentException("Billet déjà annulé");
        } else {
            LocalDate dateValidite = billet.getDate();
            LocalDate dateAnnulationLimite = LocalDate.now().plusDays(7);

            if (dateAnnulationLimite.isAfter(dateValidite)) {
                throw new IllegalArgumentException("La période d'annulation est dépassée");
            }

            billet.setEstConfirme(false);
            billetRepository.save(billet);
            return billet;
        }
    }

    /**
     * Récupérer tous les billets reservés
     * @return
     */
    public List<Billet> getBilletsReserves(){
        return billetRepository.getBilletByEtat(BilletEtat.RESREVE);
    }

    /**
     * Récupérer tous les billets payés
     * @return
     */
    public List<Billet> getBilletsPayes(){
        return billetRepository.getBilletByEtat(BilletEtat.PAYE);
    }

    /**
     * Récupérer tous les billets reservés par un visiteur
     * @param id
     * @return
     */
    public List<Billet> getBilletsReservesByVisiteur(Long id){
        Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Visiteur introuvable"));
        List<Billet> billets = billetRepository.getBilletByEtatAndVisiteur_Id(BilletEtat.RESREVE, id);
        if( billets.isEmpty() ) throw new IllegalArgumentException("Aucun billet reservé");
        else
            return billetRepository.getBilletByEtatAndVisiteur_Id(BilletEtat.RESREVE, id);

    }

    /**
     * Récupérer tous les billets payés par un visiteur
     * @param id
     * @return
     */
    public List<Billet> getBilletsPayesByVisiteur(Long id){
        Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Visiteur introuvable"));
        List<Billet> billets = billetRepository.getBilletByEtatAndVisiteur_Id(BilletEtat.PAYE, id);
        if( billets.isEmpty() ) throw new IllegalArgumentException("Aucun billet payé");
        else
            return billetRepository.getBilletByEtatAndVisiteur_Id(BilletEtat.PAYE, id);    }
}
