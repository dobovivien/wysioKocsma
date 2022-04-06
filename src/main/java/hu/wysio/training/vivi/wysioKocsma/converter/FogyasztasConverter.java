package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FogyasztasConverter {

    public FogyasztasDto convertFogyasztasToDto(Fogyasztas fogyasztas) {
        FogyasztasDto dto = new FogyasztasDto();
        dto.setItalId(fogyasztas.getItal().getId());
        dto.setElfogyasztottMennyiseg(fogyasztas.getElfogyasztottMennyiseg());
        return dto;
    }

    public Fogyasztas convertDtoToFogyasztas(FogyasztasDto fogyasztasDto) {
        Ital ital = new Ital();
        ital.setId(fogyasztasDto.getItalId());
        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setId(fogyasztasDto.getKocsmazasId());
        Fogyasztas fogyasztas = new Fogyasztas();
        fogyasztas.setElfogyasztottMennyiseg(fogyasztasDto.getElfogyasztottMennyiseg());
        fogyasztas.setItal(ital);
        fogyasztas.setKocsmazas(kocsmazas);
        return fogyasztas;
    }

    public List<Long> convertFogyasztasListToFogyasztasDtoList(List<Fogyasztas> fogyasztasok) {
        List<Long> fogyasztasDtoList = new ArrayList<>();
        for (Fogyasztas fogyasztas : fogyasztasok) {
            fogyasztasDtoList.add(fogyasztas.getId());
        }
        return fogyasztasDtoList;
    }

    public List<Fogyasztas> convertFogyasztasDtoListToFogyasztasList(List<FogyasztasDto> fogyasztasokDto) {
        List<Fogyasztas> fogyasztasList = new ArrayList<>();
        for (FogyasztasDto fogyasztas : fogyasztasokDto) {
            fogyasztasList.add(convertDtoToFogyasztas(fogyasztas));
        }
        return fogyasztasList;
    }
}
