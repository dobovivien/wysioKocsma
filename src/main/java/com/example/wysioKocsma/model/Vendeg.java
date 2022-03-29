package com.example.wysioKocsma.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="vendeg")
public class Vendeg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long vendegId;

    @Column(name = "becenev")
    private String becenev;

    @Column(name = "majerosseg")
    private String majerosseg;

    @Column(name = "bicepszmeret")
    private int bicepszmeret;

}
