package hu.wysio.training.vivi.wysioKocsma.dto;

import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.Majerosseg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendegDto implements Serializable {

    @NotNull
    private long id;

    @NotEmpty
    private String nev;

    @NotNull
    @Enumerated
    private Majerosseg majerosseg;

    @NotNull
    @Positive
    private int bicepszmeret;

    private List<Bunyo> bunyoList;

    private List<Kocsmazas> kocsmazasList;
}
