package hu.wysio.training.vivi.wysiokocsma.dto;

import hu.wysio.training.vivi.wysiokocsma.model.Bunyo;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.Majerosseg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
