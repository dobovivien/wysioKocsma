package hu.wysio.training.vivi.wysiokocsma.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TabellaDto implements Serializable, Comparable<TabellaDto> {

    private String resztvevoNeve;
    private Long gyozelmekSzama;
    private Long bunyokbanReszvetelSzama;
    private Double gyozelmiArany;

    @Override
    public int compareTo(TabellaDto masikDto) {
        return Integer.compare(Math.toIntExact(getGyozelmekSzama()), Math.toIntExact(masikDto.getGyozelmekSzama()));
    }
}
