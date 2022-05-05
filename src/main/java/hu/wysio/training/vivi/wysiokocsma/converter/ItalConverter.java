package hu.wysio.training.vivi.wysiokocsma.converter;

import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
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

    public Ital convertDtoToItal(ItalDto italDto) {
        Ital ital = new Ital();
        ital.setNev(italDto.getItalNev());
        ital.setAlkoholTartalom(italDto.getAlkoholTartalom());
        ital.setAdagMennyisege(italDto.getAdagMennyisege());
        return ital;
    }
}
