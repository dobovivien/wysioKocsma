package hu.wysio.training.vivi.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bunyo_vendeg", joinColumns = @JoinColumn(name = "bunyo_id"), inverseJoinColumns = @JoinColumn(name = "vendeg_id"))
    private Set<Vendeg> vendegList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nyertesId")
    private Vendeg nyertes;

}
