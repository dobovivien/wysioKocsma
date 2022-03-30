package hu.wysio.training.vivi.wysioKocsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItalDto implements Serializable {

    private String italNev;
    private int alkoholTartalom;
    private int adagMennyisege;
}
