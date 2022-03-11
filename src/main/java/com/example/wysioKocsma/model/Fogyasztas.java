package com.example.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="fogyasztas")
@Data
@AllArgsConstructor
public class Fogyasztas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "ital")
    private String ital;
    @Column(name = "elfogyasztott_egyseg")
    private int elfogyasztott_egyseg;

    public Fogyasztas() {

    }
}
