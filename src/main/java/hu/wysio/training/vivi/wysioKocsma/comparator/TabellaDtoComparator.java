package hu.wysio.training.vivi.wysioKocsma.comparator;

import hu.wysio.training.vivi.wysioKocsma.dto.TabellaDto;

import java.util.Comparator;

public class TabellaDtoComparator implements Comparator<TabellaDto> {

    //a bunyókban való részvételek és győzelmek számának arányát hasonlítja össze
    @Override
    public int compare(TabellaDto tabellaDto1, TabellaDto tabellaDto2) {
        return (int) (tabellaDto1.getGyozelmiArany() - (tabellaDto2.getGyozelmiArany()));
    }
}
