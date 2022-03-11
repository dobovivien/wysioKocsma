package com.example.wysioKocsma.repository;

import com.example.wysioKocsma.model.Vendeg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KocsmaRepository extends JpaRepository<Vendeg, Long> {

    List<Vendeg> findAll();

}
