package com.example.miageland.repositories;

import com.example.miageland.entities.Employe;
import com.example.miageland.entities.EmployeRole;
import com.example.miageland.entities.Visiteur;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeRepository extends JpaRepository <Employe, Long> {
    Employe getEmployeByEmail(String email);

    Employe getEmployeByNom(String nom);

    Employe getEmployeByNomAndEmail(String nom, String email);

    Employe findEmployeByNomAndEmail( String nom, String email );

    @Transactional
    Integer deleteEmployeById(Long id);

    Employe deleteEmployeByEmail(String email);

    List<Employe> findEmployeByRole(EmployeRole role);

    Boolean existsEmployeById(Long id);
    Boolean existsEmployeByEmail(String email);


}
