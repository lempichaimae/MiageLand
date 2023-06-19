package com.example.miageland.repositories;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.BilletEtat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BilletRepository extends JpaRepository <Billet, Long> {

    Billet getBilletByNumBillet(Long numBillet);


//    Billet getBilletByEstValide(boolean estValide);


    List<Billet> getBilletByDate(Date date);

    List<Billet> getBilletByEtat(BilletEtat etat);


    List<Billet> getBilletByEtatAndVisiteur_Id(BilletEtat etat, Long id);



    Billet deleteBilletByNumBillet(Long numBillet);

    @Query(value = "SELECT RAND() * (max_price - min_price) + min_price FROM billet", nativeQuery = true)
    Double genererPrixAleatoire();


    int countByEtat(BilletEtat billetEtat);
    @Query("Select sum(prix) from Billet")
    double getRecetteTotale();

    @Query("Select distinct date from Billet")
    List<LocalDate> getAllDate();

    int countByDateAndEtat(LocalDate date, BilletEtat paye);
    @Query("SELECT SUM(prix) FROM Billet WHERE DATE(date) = :localdate GROUP BY DATE(date)")
    double getRecetteByDate(@Param("localdate") LocalDate localdate);

}

