package com.example.wysioKocsma.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ital")
public class Ital {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int italId;

    @Column(name = "nev")
    private String nev;

    @Column(name = "alkohol_tartalom")
    private int alkohol_tartalom;

    @Column(name = "adag_mennyisege")
    private int adag_mennyisege;

}
