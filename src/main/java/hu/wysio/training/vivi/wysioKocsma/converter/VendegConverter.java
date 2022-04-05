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
        dto.setId(vendeg.getId());
        dto.setNev(vendeg.getBecenev());
        dto.setMajerosseg(vendeg.getMajerosseg());
        dto.setBicepszmeret(vendeg.getBicepszmeret());
        return dto;
    }

    public Vendeg convertDtoToVendeg(VendegDto vendegDto) {
        Vendeg vendeg = new Vendeg();
        vendeg.setId(vendegDto.getId());
        vendeg.setBecenev(vendegDto.getNev());
        vendeg.setMajerosseg(vendegDto.getMajerosseg());
        vendeg.setBicepszmeret(vendegDto.getBicepszmeret());
        return vendeg;
    }

    public List<VendegDto> convertVendegListToVendegListDto(List<Vendeg> vendegekList) {
        List<VendegDto> vendegDtoList = new ArrayList<>();
        for (Vendeg vendeg : vendegekList) {
            vendegDtoList.add(convertVendegToDto(vendeg));
        }
        return vendegDtoList;
    }

    public List<Vendeg> convertVendegDtoListToVendegList(List<VendegDto> vendegekDtoList) {
        List<Vendeg> vendegList = new ArrayList<>();
        for (VendegDto vendeg : vendegekDtoList) {
            vendegList.add(convertDtoToVendeg(vendeg));
        }
        return vendegList;
    }
}
