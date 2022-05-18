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
    private Long gyozelmekSzama;
    private Long bunyokbanReszvetelSzama;
    private Double gyozelmiArany;

    @Override
    public int compareTo(TabellaDto masikDto) {
        return Integer.compare(Math.toIntExact(getGyozelmekSzama()), Math.toIntExact(masikDto.getGyozelmekSzama()));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
