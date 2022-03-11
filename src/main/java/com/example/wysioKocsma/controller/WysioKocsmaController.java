package com.example.wysioKocsma.controller;

import com.example.wysioKocsma.model.Vendeg;
import com.example.wysioKocsma.repository.KocsmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class WysioKocsmaController {
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @Autowired
    KocsmaRepository kocsmaRepository;
    @GetMapping("/vendegek")
    public ResponseEntity<List<Vendeg>> getAllVendeg(@RequestParam(required = false) String becenev) {
        try {
            List<Vendeg> vendegek = new ArrayList<>();
            vendegek.addAll(kocsmaRepository.findAll());
            if (vendegek.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vendegek, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
