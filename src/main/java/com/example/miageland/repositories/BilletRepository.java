package com.example.miageland.repositories;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BilletRepository extends JpaRepository <Billet, Long> {

    Billet getBilletByNumBillet(Long numBillet);


//    Billet getBilletByEstValide(boolean estValide);


    List<Billet> getBilletByDate(Date date);

    Billet deleteBilletByNumBillet(Long numBillet);

    @Query(value = "SELECT RAND() * (max_price - min_price) + min_price FROM billet", nativeQuery = true)
    Double genererPrixAleatoire();


}
