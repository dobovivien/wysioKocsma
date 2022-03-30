package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;

public class VendegConverter {

    public static VendegDto convertVendegToDto(Vendeg vendeg) {
        VendegDto dto = new VendegDto();
        dto.setNev(vendeg.getBecenev());
        dto.setMajerosseg(vendeg.getMajerosseg());
        dto.setBicepszmeret(vendeg.getBicepszmeret());
        return dto;
    }

}
