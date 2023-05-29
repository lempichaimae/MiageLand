package com.example.miageland.services;

import com.example.miageland.entities.Billet;
import com.example.miageland.repositories.BilletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BilletService {

    private BilletRepository billetRepository;

    @Autowired
    public BilletService(BilletRepository billetRepository) {
        this.billetRepository = billetRepository;
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

}
