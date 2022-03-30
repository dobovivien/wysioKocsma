package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;

public class FogyasztasConverter {

    public static FogyasztasDto convertFogyasztasToDto(Fogyasztas fogyasztas) {
        FogyasztasDto dto = new FogyasztasDto();
        dto.setItalNeve(fogyasztas.getItal().getNev());
        dto.setElfogyasztottMennyiseg(fogyasztas.getElfogyasztottMennyiseg());
        return dto;
    }
}
