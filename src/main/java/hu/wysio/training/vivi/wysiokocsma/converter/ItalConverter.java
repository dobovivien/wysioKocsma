package hu.wysio.training.vivi.wysiokocsma.converter;

import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import org.springframework.stereotype.Component;

@Component
public class ItalConverter {

    public Ital toItal(ItalDto italDto) {
        Ital ital = new Ital();

        ital.setNev(italDto.getItalNev());
        ital.setAlkoholTartalom(italDto.getAlkoholTartalom());
        ital.setAdagMennyisege(italDto.getAdagMennyisege());

        return ital;
    }
}
