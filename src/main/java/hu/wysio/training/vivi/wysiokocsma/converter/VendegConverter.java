package hu.wysio.training.vivi.wysiokocsma.converter;

import hu.wysio.training.vivi.wysiokocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import org.springframework.stereotype.Component;

@Component
public class VendegConverter {

    public Vendeg toVendeg(VendegDto vendegDto) {
        Vendeg vendeg = new Vendeg();

        vendeg.setId(vendegDto.getId());
        vendeg.setBecenev(vendegDto.getNev());
        vendeg.setMajerosseg(vendegDto.getMajerosseg());
        vendeg.setBicepszmeret(vendegDto.getBicepszmeret());
        vendeg.setBunyoList(vendegDto.getBunyoList());
        vendeg.setKocsmazasList(vendegDto.getKocsmazasList());

        return vendeg;
    }

    public TabellaDto toTabellaDto(Vendeg vendeg, long bunyokSzama, int gyozelmekSzama) {
        TabellaDto tabellaDto = new TabellaDto();

        tabellaDto.setResztvevoNeve(vendeg.getBecenev());
        tabellaDto.setBunyokbanReszvetelSzama(bunyokSzama);
        tabellaDto.setGyozelmekSzama(gyozelmekSzama);
        tabellaDto.setGyozelmiArany((double) gyozelmekSzama / bunyokSzama * 100);

        return tabellaDto;
    }

    public VendegFogyasztasSzerintDto toVendegFogyasztasSzerintDto(Vendeg vendeg, long fogyasztottMennyiseg) {
        VendegFogyasztasSzerintDto vendegFogyasztasSzerintDto = new VendegFogyasztasSzerintDto();

        vendegFogyasztasSzerintDto.setBecenev(vendeg.getBecenev());
        vendegFogyasztasSzerintDto.setElfogyasztottMennyiseg(fogyasztottMennyiseg);

        return vendegFogyasztasSzerintDto;
    }
}
