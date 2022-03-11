package com.example.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="bunyo")
@Data
@AllArgsConstructor
public class Bunyo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "mettol")
    private Date mettol;
    @Column(name = "meddig")
    private Date meddig;
    @Column(name = "resztvevok")
    private String resztvevok;
    @Column(name = "nyertes")
    private String nyertes;

    public Bunyo() {

    }
}
