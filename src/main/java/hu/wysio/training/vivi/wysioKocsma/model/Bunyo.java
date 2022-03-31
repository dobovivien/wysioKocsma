package hu.wysio.training.vivi.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Bunyo extends AbstractEntity {

    @Column
    private LocalDateTime mettol;

    @Column
    private LocalDateTime meddig;

    @CollectionTable
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true)
    private List<Vendeg> resztvevok = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nyertesId", nullable = false, unique = true)
    private Vendeg nyertes;

}
