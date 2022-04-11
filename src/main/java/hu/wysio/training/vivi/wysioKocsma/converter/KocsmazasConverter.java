package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KocsmazasConverter {

    @Autowired
    private FogyasztasConverter fogyasztasConverter;

    public KocsmazasDto convertKocsmazasToDto(Kocsmazas kocsmazas) {
        KocsmazasDto dto = new KocsmazasDto();
        dto.setDetoxbaKerult(kocsmazas.isDetoxbaKerult());
        dto.setFogyasztasLista(fogyasztasConverter.convertFogyasztasListToFogyasztasDtoList(kocsmazas.getFogyasztasLista()));
        return dto;
    }

    public Kocsmazas convertDtoToKocsmazas(KocsmazasDto kocsmazasDto) {
        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setDetoxbaKerult(kocsmazas.isDetoxbaKerult());
        kocsmazas.setFogyasztasLista(kocsmazas.getFogyasztasLista());
        return kocsmazas;
    }
}
