package hu.wysio.training.vivi.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ital", schema = "public")
public class Ital extends AbstractEntity {

    @Column
    private String nev;

    @Column(name = "alkohol_tartalom")
    private int alkoholTartalom;

    @Column(name = "adag_mennyisege")
    private int adagMennyisege;

}
