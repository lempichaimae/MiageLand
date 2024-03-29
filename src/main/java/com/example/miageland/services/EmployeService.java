package com.example.miageland.services;

import com.example.miageland.entities.Employe;
import com.example.miageland.entities.EmployeRole;
import com.example.miageland.repositories.EmployeRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeService {

    @Autowired
    EmployeRepository employeRepository;

    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    public Employe findByUsername(String nom) {
        return employeRepository.getEmployeByNom(nom);
    }

    public Employe findByEmail(String email){
        if(employeRepository.getEmployeByEmail(email) != null) {
            return employeRepository.getEmployeByEmail(email);
        }
        throw new IllegalArgumentException("User not found !");
    }

    public boolean checkCredentials(String nom, String email) {

        if (employeRepository.getEmployeByNomAndEmail(nom, email) != null) {
            return true;
        }
        return false;
    }

    public boolean singIn(Employe employe) {
        Employe existingEmploye = employeRepository.getEmployeByNomAndEmail(employe.getNom(), employe.getEmail());
        if (existingEmploye != null) {
            throw new IllegalArgumentException("Vous êtes déjà inscrit !");
        } else {
            boolean manager = employe.isManager();
//            System.out.println("Valeur de isManager : " + manager);
            if (manager) {
                employeRepository.save(employe);
                return true;
            } else {
                throw new IllegalArgumentException("Vous n'avez pas le statut de manager !");
            }
        }
    }

    public void ajouterEmploye(Employe employe) {
        // Vérifier si l'employe existe déjà
        @NonNull
        String nom = employe.getNom();
        if (employeRepository.getEmployeByNom(nom) != null) {
            throw new IllegalArgumentException("Employe existant");
        }

        employeRepository.save(employe);

    }

    @Transactional
    public void supprimerEmploye(Long id) {
        // Vérifier si l'employe existe déjà
        if (employeRepository.existsEmployeById(id)) {
            employeRepository.deleteEmployeById(id);
        } else {
            throw new IllegalArgumentException("L'attraction n'existe pas");
        }
    }

    public EmployeRole isAdmin(String email){
        if(employeRepository.existsEmployeByEmail(email)){
            EmployeRole role= employeRepository.getEmployeByEmail(email).getRole();
            return role;
        }
        return null;
    }



//    public boolean isManager(String email) {
//        if (employeRepository.getEmployeByEmail(email).isManager()) {
//            return true;
//        }
//        return false;
//    }
}
