package com.example.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="kocsmazas")
@Data
@AllArgsConstructor
public class Kocsmazas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "mettol")
    private Date mettol;
    @Column(name = "meddig")
    private Date meddig;
    @Column(name = "fogyasztas_lista")
    private String fogyasztas_lista;
    @Column(name = "detoxba_kerult")
    private boolean detoxba_kerult;

    public Kocsmazas() {

    }
}
