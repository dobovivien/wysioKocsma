package hu.wysio.training.vivi.wysiokocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Vendeg extends AbstractEntity {

    @Column(nullable = false)
    @NotEmpty
    private String becenev;

    @Column(nullable = false)
    @Enumerated
    @NotNull
    private Majerosseg majerosseg;

    @Column(nullable = false)
    @NotNull
    @Positive
    private int bicepszmeret;

    @OneToMany(mappedBy = "vendeg", fetch = FetchType.LAZY)
    private List<Kocsmazas> kocsmazasList;

    @ManyToMany(mappedBy = "vendegList")
    private List<Bunyo> bunyoList;
}
