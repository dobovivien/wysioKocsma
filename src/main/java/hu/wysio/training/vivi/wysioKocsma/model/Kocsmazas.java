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
@Table(name = "kocsmazas", schema = "public")
public class Kocsmazas extends AbstractEntity {

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime mettol;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime meddig;

    @OneToMany(mappedBy = "kocsmazas", fetch = FetchType.LAZY)
    private List<Fogyasztas> fogyasztasLista;

    @Column(name = "detoxba_kerult")
    private boolean detoxbaKerult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendeg_id", nullable = false)
    private Vendeg vendeg;

}
