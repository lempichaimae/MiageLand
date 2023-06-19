package com.example.miageland.dataloader;

import com.example.miageland.entities.*;
import com.example.miageland.services.AttractionService;
import com.example.miageland.services.BilletService;
import com.example.miageland.services.EmployeService;
import com.example.miageland.services.VisiteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component

public class DataLoader
        implements ApplicationRunner
{
    @Autowired
    private
    AttractionService attractionService;

    @Autowired
    private
    EmployeService employeeService;

    @Autowired

    private
    VisiteurService visitorService;

    @Autowired
    private
    BilletService billetService;

    private
    Visiteur visiteur1;

    private
    Visiteur visiteur2;

    private
    Employe employee1;

    private
    Employe employee2;

    private
    Employe employee3;

    @Override
    public void
    run(ApplicationArguments
                args) {
        generateData();

    }

    private void
    generateData() {

        generateSampleAttractions();

        generateSampleEmployees();

        generateSampleVisiteurs();

        generateSampleTickets();

    }

    private void
    generateSampleAttractions() {

        Attraction
                attraction1 = new
                Attraction(001L,"Tramplin de la mort",true,visiteur1,employee1);

        Attraction
                attraction2 = new
                Attraction(002L,"Saut périlleux",true,visiteur2,employee2);

        Attraction
                attraction3 = new
                Attraction(002L,"Blue Lagoon",false,visiteur2,employee3);

        attractionService.ajouterAttraction(attraction1);

        attractionService.ajouterAttraction(attraction2);

        attractionService.ajouterAttraction(attraction3);

    }

    private void
    generateSampleEmployees() {

        employee1 = new
        Employe(001L,"Jean",
        "Jean",
        "j.jean@toulouse.miage.fr",true, null,null);

        employee2 = new
        Employe(002L,"Bob",
        "Bob",
        "b.bob@toulouse.miage.fr",false,EmployeRole.Validateur,null);

        employee3 = new
        Employe(003L,"Jonathan",
        "Marley",
        "jonathan.marley@toulouse.miage.fr",false,EmployeRole.Administrateur,null);

        employeeService.ajouterEmploye(employee1);

        employeeService.ajouterEmploye(employee2);

        employeeService.ajouterEmploye(employee3);

    }

    private void
    generateSampleVisiteurs() {

        visiteur1
                = new
                Visiteur(001L,"Sumia",
                "Susu",
                "sumia.susu@toulouse.miage.fr",null,null);

        visiteur2
                = new
                Visiteur(002L,"Chaimae",
                "Rabat",
                "chaimae.rabat@toulouse.miage.fr",null,null);

        visitorService.singIn(visiteur1);

        visitorService.singIn(visiteur2);

    }

    private void
    generateSampleTickets() {

        LocalDate
                yesterdayDate = LocalDate.now().minusDays(1);

        LocalDate
                todayDate = LocalDate.now();

        LocalDate
                comingDate = LocalDate.now().plusDays(5);
        billetService.reserverBillet(001L,todayDate);

        billetService.payerBillet(001L);

        billetService.reserverBillet(002L,yesterdayDate);
        billetService.reserverBillet(001L,todayDate);
        billetService.payerBillet(003L);

    }

}
