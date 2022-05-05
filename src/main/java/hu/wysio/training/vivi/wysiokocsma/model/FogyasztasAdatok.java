package hu.wysio.training.vivi.wysiokocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FogyasztasAdatok {

    private int elfogyasztottMennyiseg;
    private int alkoholTartalom;
    private int adagMennyisege;
}
