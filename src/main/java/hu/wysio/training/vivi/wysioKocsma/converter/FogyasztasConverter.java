package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;

import java.util.ArrayList;
import java.util.List;

public class FogyasztasConverter {

    public static FogyasztasDto convertFogyasztasToDto(Fogyasztas fogyasztas) {
        FogyasztasDto dto = new FogyasztasDto();
        dto.setItalNeve(fogyasztas.getItal().getNev());
        dto.setElfogyasztottMennyiseg(fogyasztas.getElfogyasztottMennyiseg());
        return dto;
    }

    public static List<FogyasztasDto> listConverter(List<Fogyasztas> fogyasztasok) {
        List<FogyasztasDto> fogyasztasDtoList = new ArrayList<>();
        for (Fogyasztas fogyasztas : fogyasztasok) {
            fogyasztasDtoList.add(FogyasztasConverter.convertFogyasztasToDto(fogyasztas));
        }
        return fogyasztasDtoList;
    }
}
