package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import org.springframework.stereotype.Component;

@Component
public class ItalConverter {

    public ItalDto convertItalToDto(Ital ital) {
        ItalDto dto = new ItalDto();
        dto.setItalNev(ital.getNev());
        dto.setAlkoholTartalom(ital.getAlkoholTartalom());
        dto.setAdagMennyisege(ital.getAdagMennyisege());
        return dto;
    }
}
