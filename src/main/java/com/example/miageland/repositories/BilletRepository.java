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

    List<Billet> getBilletByEtat(BilletEtat etat);

    List<Billet> getBilletByEtatAndVisiteur_Id(BilletEtat etat, Long id);

    int countByEtat(BilletEtat billetEtat);
    @Query("Select sum(prix) from Billet")
    double getRecetteTotale();

    @Query("Select distinct date from Billet")
    List<LocalDate> getAllDate();

    int countByDateAndEtat(LocalDate date, BilletEtat paye);

    @Query("SELECT SUM(prix) FROM Billet WHERE DATE(date) = :localdate GROUP BY DATE(date)")
    double getRecetteByDate(@Param("localdate") LocalDate localdate);

    @Query(value = "SELECT COUNT(*) AS max_billets FROM billet WHERE date >= :date GROUP BY date ORDER BY max_billets DESC LIMIT 1", nativeQuery = true)
    int getMaxInFutureDates(@Param("date") LocalDate date);

    int countByDate(LocalDate date);
}

