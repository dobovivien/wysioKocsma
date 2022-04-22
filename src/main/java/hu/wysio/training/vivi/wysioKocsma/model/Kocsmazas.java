package hu.wysio.training.vivi.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kocsmazas", schema = "public")
public class Kocsmazas extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendeg_id", nullable = false)
    private Vendeg vendeg;

    @Column
    private LocalDateTime mettol;

    @Column
    private LocalDateTime meddig;

    @OneToMany(mappedBy = "kocsmazas", fetch = FetchType.LAZY)
    private List<Fogyasztas> fogyasztasLista;

    @Column(name = "detoxba_kerult")
    private boolean detoxbaKerult;


}
