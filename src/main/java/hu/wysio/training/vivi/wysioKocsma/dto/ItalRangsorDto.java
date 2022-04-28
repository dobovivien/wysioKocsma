package hu.wysio.training.vivi.wysioKocsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Tuple;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItalRangsorDto implements Serializable {

    private String italNeve;
    private long fogyasztottMennyiseg;

    public ItalRangsorDto(Tuple tuple) {
        this.italNeve = tuple.get(0, String.class);
        this.fogyasztottMennyiseg = tuple.get(1, Long.class);
    }
}
