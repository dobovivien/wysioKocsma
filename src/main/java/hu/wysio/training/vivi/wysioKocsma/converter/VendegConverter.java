package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;

import java.util.ArrayList;
import java.util.List;

public class VendegConverter {

    public static VendegDto convertVendegToDto(Vendeg vendeg) {
        VendegDto dto = new VendegDto();
        dto.setNev(vendeg.getBecenev());
        dto.setMajerosseg(vendeg.getMajerosseg());
        dto.setBicepszmeret(vendeg.getBicepszmeret());
        return dto;
    }

    public static List<VendegDto> listConverter(List<Vendeg> vendegek) {
        List<VendegDto> vendegDtoList = new ArrayList<>();
        for (Vendeg vendeg : vendegek) {
            vendegDtoList.add(VendegConverter.convertVendegToDto(vendeg));
        }
        return vendegDtoList;
    }
}
