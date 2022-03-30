package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;

public class KocsmazasConverter {

    public static KocsmazasDto convertKocsmazasToDto(Kocsmazas kocsmazas) {
        KocsmazasDto dto = new KocsmazasDto();
        dto.setDetoxbaKerult(kocsmazas.isDetoxbaKerult());
        dto.setFogyasztasLista(kocsmazas.getFogyasztasLista());
        return dto;
    }
}
