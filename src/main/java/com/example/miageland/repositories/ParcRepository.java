package com.example.miageland.repositories;

import com.example.miageland.entities.Parc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcRepository extends JpaRepository<Parc, Long> {
}
