package com.example.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="vendeg")
@Data
@AllArgsConstructor
public class Vendeg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "becenev")
    private String becenev;
    @Column(name = "majerosseg")
    private String majerosseg;
    @Column(name = "bicepszmeret")
    private int bicepszmeret;

    public Vendeg() {

    }
}
