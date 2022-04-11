package hu.wysio.training.vivi.wysioKocsma.dto;

import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendegDto implements Serializable {

    private long id;
    private String nev;
    private String majerosseg;
    private int bicepszmeret;
    private List<Bunyo> bunyoList;
    private List<Kocsmazas> kocsmazasList;
}
