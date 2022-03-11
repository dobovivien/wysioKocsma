package com.example.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="ital")
@Data
@AllArgsConstructor
public class Ital {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "nev")
    private String nev;
    @Column(name = "alkohol_tartalom")
    private int alkohol_tartalom;
    @Column(name = "adag_mennyisege")
    private int adag_mennyisege;

    public Ital() {

    }
}
