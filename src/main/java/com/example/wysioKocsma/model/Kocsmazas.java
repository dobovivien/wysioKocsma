package com.example.wysioKocsma.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="kocsmazas")
public class Kocsmazas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int kocsmazasId;

    @Column(name = "mettol")
    private Date mettol;

    @Column(name = "meddig")
    private Date meddig;

    @Column(name = "fogyasztas_lista", columnDefinition = "int[]")
    @Type(type = AbstractEntity.ARRAY_TYPE)
    @JoinColumn (name="italId", nullable=false, unique=true)
    private List<Integer> fogyasztas_lista = new ArrayList<>();

    @Column(name = "detoxba_kerult")
    private boolean detoxba_kerult;

}
