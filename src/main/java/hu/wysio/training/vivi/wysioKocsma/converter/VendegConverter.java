package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendegConverter {

    public VendegDto convertVendegToDto(Vendeg vendeg) {
        VendegDto dto = new VendegDto();
        dto.setNev(vendeg.getBecenev());
        dto.setMajerosseg(vendeg.getMajerosseg());
        dto.setBicepszmeret(vendeg.getBicepszmeret());
        return dto;
    }

    public List<VendegDto> listConverter(List<Vendeg> vendegek) {
        List<VendegDto> vendegDtoList = new ArrayList<>();
        for (Vendeg vendeg : vendegek) {
            vendegDtoList.add(convertVendegToDto(vendeg));
        }
        return vendegDtoList;
    }
}
