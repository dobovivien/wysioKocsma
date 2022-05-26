package hu.wysio.training.vivi.wysiokocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Ital extends AbstractEntity {

    @Column(nullable = false)
    @NotEmpty
    private String nev;

    @Column(nullable = false)
    @NotNull
    @Positive
    private int alkoholTartalom;

    @Column(nullable = false)
    @NotNull
    @Positive
    private int adagMennyisege;

}
