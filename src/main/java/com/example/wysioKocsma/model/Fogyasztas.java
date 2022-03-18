package com.example.wysioKocsma.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="fogyasztas")
public class Fogyasztas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fogyasztasId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="id", nullable=false, unique=true)
    private Ital ital;

    @Column(name = "italId")
    @JoinColumn (name="italId", nullable=false, unique=true)
    private int italId;

    @Column(name = "elfogyasztott_egyseg")
    private int elfogyasztott_egyseg;

}
