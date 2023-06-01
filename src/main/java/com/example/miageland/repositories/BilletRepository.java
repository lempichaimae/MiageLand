package com.example.miageland.repositories;

import com.example.miageland.entities.Billet;
import com.example.miageland.entities.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BilletRepository extends JpaRepository <Billet, Long> {

    Billet getBilletByNumBillet(Long numBillet);


//    Billet getBilletByEstValide(boolean estValide);


    List<Billet> getBilletByDate(Date date);

    Billet deleteBilletByNumBillet(Long numBillet);


}
