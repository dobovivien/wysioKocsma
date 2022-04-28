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
public class TabellaDto implements Serializable, Comparable<TabellaDto> {

    private String resztvevoNeve;
    private int gyozelmekSzama;
    private long bunyokbanReszvetelSzama;
    private double gyozelmiArany;

    @Override
    public int compareTo(TabellaDto masikDto) {
        return Integer.compare(getGyozelmekSzama(), masikDto.getGyozelmekSzama());
    }
}
