package hu.wysio.training.vivi.wysioKocsma.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fogyasztas", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fogyasztas extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ital_id", nullable = false)
    private Ital ital;

    @Column(name = "elfogyasztott_mennyiseg")
    private int elfogyasztottMennyiseg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kocsmazas_id", nullable = false)
    private Kocsmazas kocsmazas;

}
