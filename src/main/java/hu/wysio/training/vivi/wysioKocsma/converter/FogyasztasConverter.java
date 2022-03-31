package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FogyasztasConverter {

    public FogyasztasDto convertFogyasztasToDto(Fogyasztas fogyasztas) {
        FogyasztasDto dto = new FogyasztasDto();
        dto.setItalNeve(fogyasztas.getItal().getNev());
        dto.setElfogyasztottMennyiseg(fogyasztas.getElfogyasztottMennyiseg());
        return dto;
    }

    public List<FogyasztasDto> listConverter(List<Fogyasztas> fogyasztasok) {
        List<FogyasztasDto> fogyasztasDtoList = new ArrayList<>();
        for (Fogyasztas fogyasztas : fogyasztasok) {
            fogyasztasDtoList.add(convertFogyasztasToDto(fogyasztas));
        }
        return fogyasztasDtoList;
    }
}
