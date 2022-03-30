package hu.wysio.training.vivi.wysioKocsma.dto;

import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
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
public class BunyoDto implements Serializable {

    private List<Vendeg> resztvevok;
    private String nyertes;
}
