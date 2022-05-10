package hu.wysio.training.vivi.wysiokocsma.dto;

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
    private Integer gyozelmekSzama;
    private Long bunyokbanReszvetelSzama;
    private Double gyozelmiArany;

    @Override
    public int compareTo(TabellaDto masikDto) {
        return Integer.compare(getGyozelmekSzama(), masikDto.getGyozelmekSzama());
    }
}
