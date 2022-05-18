package hu.wysio.training.vivi.wysiokocsma.converter;

import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;

@Component
public class ItalConverter {

    public Ital toEntity(ItalDto italDto) {
        Ital ital = new Ital();

        ital.setNev(italDto.getItalNev());
        ital.setAlkoholTartalom(italDto.getAlkoholTartalom());
        ital.setAdagMennyisege(italDto.getAdagMennyisege());

        return ital;
    }

    public ItalRangsorDto toItalRangsorDto(Tuple tuple) {
        ItalRangsorDto italRangsorDto = new ItalRangsorDto();

        italRangsorDto.setItalNeve(tuple.get(0, String.class));
        italRangsorDto.setFogyasztottMennyiseg(tuple.get(1, Long.class));

        return italRangsorDto;
    }

}
