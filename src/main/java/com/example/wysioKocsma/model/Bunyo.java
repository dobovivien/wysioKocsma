package com.example.wysioKocsma.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.sun.source.tree.Tree.Kind.ARRAY_TYPE;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bunyo")
public class Bunyo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bunyoId;

    @Column(name = "mettol")
    private Date mettol;

    @Column(name = "meddig")
    private Date meddig;

    @Column(name = "resztvevok", columnDefinition = "int[]")
    @Type(type = AbstractEntity.ARRAY_TYPE)
    @JoinColumn (name="vendegId", nullable=false, unique=true)
    private List<Integer> resztvevok = new ArrayList<>();

    @Column(name = "nyertesId")
    @JoinColumn (name="vendegId", nullable=false, unique=true)
    private int nyertesId;

}
