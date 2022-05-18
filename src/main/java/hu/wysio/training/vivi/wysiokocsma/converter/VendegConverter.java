package hu.wysio.training.vivi.wysiokocsma.converter;

import hu.wysio.training.vivi.wysiokocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import org.springframework.stereotype.Component;

@Component
public class VendegConverter {

    public Vendeg toEntity(VendegDto vendegDto) {
        Vendeg vendeg = new Vendeg();

        vendeg.setBecenev(vendegDto.getNev());
        vendeg.setMajerosseg(vendegDto.getMajerosseg());
        vendeg.setBicepszmeret(vendegDto.getBicepszmeret());
        vendeg.setBunyoList(vendegDto.getBunyoList());
        vendeg.setKocsmazasList(vendegDto.getKocsmazasList());

        return vendeg;
    }

    public TabellaDto toTabellaDto(Vendeg vendeg, long gyozelmekSzama) {
        TabellaDto tabellaDto = new TabellaDto();

        tabellaDto.setResztvevoNeve(vendeg.getBecenev());
        tabellaDto.setBunyokbanReszvetelSzama((long) vendeg.getBunyoList().size());
        tabellaDto.setGyozelmekSzama(gyozelmekSzama);
        tabellaDto.setGyozelmiArany((double) gyozelmekSzama / vendeg.getBunyoList().size() * 100);

        return tabellaDto;
    }

    public VendegFogyasztasSzerintDto toVendegFogyasztasSzerintDto(String vendegNeve, long fogyasztottMennyiseg) {
        VendegFogyasztasSzerintDto vendegFogyasztasSzerintDto = new VendegFogyasztasSzerintDto();

        vendegFogyasztasSzerintDto.setBecenev(vendegNeve);
        vendegFogyasztasSzerintDto.setElfogyasztottMennyiseg(fogyasztottMennyiseg);

        return vendegFogyasztasSzerintDto;
    }
}
