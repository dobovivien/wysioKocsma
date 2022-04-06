package hu.wysio.training.vivi.wysioKocsma.comparator;

import hu.wysio.training.vivi.wysioKocsma.dto.TabellaDto;

import java.util.Comparator;

public class TabellaDtoComparator implements Comparator<TabellaDto> {
    @Override
    public int compare(TabellaDto tabellaDto1, TabellaDto tabellaDto2) {
        return tabellaDto1.compareTo(tabellaDto2);
    }
}
