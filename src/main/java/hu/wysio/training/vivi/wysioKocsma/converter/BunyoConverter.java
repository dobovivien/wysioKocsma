package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.BunyoDto;
import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BunyoConverter {

    @Autowired
    private VendegConverter vendegConverter;

    public BunyoDto convertBunyoToDto(Bunyo bunyo) {
        BunyoDto dto = new BunyoDto();
        dto.setNyertes(bunyo.getNyertes().getBecenev());
        dto.setResztvevok(vendegConverter.listConverter(bunyo.getResztvevok()));
        return dto;

    }
}
