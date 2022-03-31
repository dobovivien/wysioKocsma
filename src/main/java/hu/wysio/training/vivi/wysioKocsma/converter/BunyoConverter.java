package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.BunyoDto;
import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;

import static hu.wysio.training.vivi.wysioKocsma.converter.VendegConverter.listConverter;

public class BunyoConverter {

    public static BunyoDto convertBunyoToDto(Bunyo bunyo) {
        BunyoDto dto = new BunyoDto();
        dto.setNyertes(bunyo.getNyertes().getBecenev());
        dto.setResztvevok(listConverter(bunyo.getResztvevok()));
        return dto;

    }
}
