package hu.wysio.training.vivi.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendeg", schema = "public")
public class Vendeg extends AbstractEntity {

    @Column(nullable = false)
    private String becenev;

    @Column(nullable = false)
    @Enumerated
    private Majerosseg majerosseg;

    @Column(nullable = false)
    private int bicepszmeret;

    @OneToMany(mappedBy = "vendeg", fetch = FetchType.LAZY)
    private List<Kocsmazas> kocsmazasList;

    @ManyToMany(mappedBy = "vendegList")
    private List<Bunyo> bunyoList;
}
