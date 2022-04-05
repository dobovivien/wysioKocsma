package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.BunyoDto;
import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BunyoConverter {

    @Autowired
    private VendegConverter vendegConverter;

    public BunyoDto convertBunyoToDto(Bunyo bunyo) {
        BunyoDto dto = new BunyoDto();
        dto.setMettol(bunyo.getMettol());
        dto.setMeddig(bunyo.getMeddig());
        dto.setNyertes(bunyo.getNyertes().getBecenev());
        dto.setResztvevok(vendegConverter.convertVendegListToVendegListDto(bunyo.getVendegList()));
        return dto;
    }

    public Bunyo convertDtoToBunyo(BunyoDto bunyoDto) {
        Vendeg nyertes = new Vendeg();
        nyertes.setId(bunyoDto.getNyertesId());
        Bunyo bunyo = new Bunyo();
        bunyo.setMettol(bunyoDto.getMettol());
        bunyo.setMeddig(bunyoDto.getMeddig());
        bunyo.setNyertes(nyertes);
        bunyo.setVendegList(vendegConverter.convertVendegDtoListToVendegList(bunyoDto.getResztvevok()));
        return bunyo;
    }
}
