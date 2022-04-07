package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendegConverter {

    @Autowired
    private VendegRepository vendegRepository;

    public VendegDto convertVendegToDto(Vendeg vendeg) {
        VendegDto dto = new VendegDto();
        dto.setId(vendeg.getId());
        dto.setNev(vendeg.getBecenev());
        dto.setMajerosseg(vendeg.getMajerosseg());
        dto.setBicepszmeret(vendeg.getBicepszmeret());
        dto.setBunyoList(vendeg.getBunyoList());
        dto.setKocsmazasList(vendeg.getKocsmazasList());
        return dto;
    }

    public Vendeg convertDtoToVendeg(VendegDto vendegDto) {
        Vendeg vendeg = new Vendeg();
        vendeg.setId(vendegDto.getId());
        vendeg.setBecenev(vendegDto.getNev());
        vendeg.setMajerosseg(vendegDto.getMajerosseg());
        vendeg.setBicepszmeret(vendegDto.getBicepszmeret());
        vendeg.setBunyoList(vendegDto.getBunyoList());
        vendeg.setKocsmazasList(vendegDto.getKocsmazasList());
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

    public TabellaDto convertVendegToTabellaDto(Vendeg vendeg, long bunyokSzama) {
        TabellaDto tabellaDto = new TabellaDto();
        tabellaDto.setResztvevoNeve(vendegRepository.getById(vendeg.getId()).getBecenev());
        tabellaDto.setBunyokbanReszvetelSzama(bunyokSzama);
        return tabellaDto;
    }

    public VendegFogyasztasSzerintDto convertVendegToVFSZDto(Vendeg vendeg, long fogyasztottMennyiseg) {
        VendegFogyasztasSzerintDto vendegFogyasztasSzerintDto = new VendegFogyasztasSzerintDto();
        vendegFogyasztasSzerintDto.setBecenev(vendegRepository.getById(vendeg.getId()).getBecenev());
        vendegFogyasztasSzerintDto.setElfogyasztottMennyiseg(fogyasztottMennyiseg);
        return vendegFogyasztasSzerintDto;
    }
}
