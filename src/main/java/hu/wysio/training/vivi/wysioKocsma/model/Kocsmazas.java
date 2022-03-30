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
public class Kocsmazas extends AbstractEntity {

    @Column
    private LocalDateTime mettol;

    @Column
    private LocalDateTime meddig;

    @CollectionTable
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fogyasztasLista", nullable = false, unique = true)
    private List<Fogyasztas> fogyasztasLista = new ArrayList<>();

    @Column
    private boolean detoxbaKerult;

}
