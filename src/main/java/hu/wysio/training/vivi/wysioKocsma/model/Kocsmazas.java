package hu.wysio.training.vivi.wysioKocsma.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table
public class Kocsmazas extends AbstractEntity {

    @Column
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime mettol;

    @Column
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime meddig;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Fogyasztas> fogyasztasLista;

    @Column
    private boolean detoxbaKerult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendegId", nullable = false)
    private Vendeg vendeg;

}
