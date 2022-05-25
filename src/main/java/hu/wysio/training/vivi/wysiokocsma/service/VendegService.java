package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendegService {

    @Autowired
    private VendegRepository vendegRepository;

    @Autowired
    private VendegConverter vendegConverter;

    public long createVendeg(VendegDto vendegDto) {
        Vendeg vendegToSave = vendegConverter.toEntity(vendegDto);

        Vendeg vendeg = vendegRepository.save(vendegToSave);

        return vendeg.getId();
    }

    public Vendeg updateVendeg(Long id, VendegDto vendegDto) {
        Vendeg updatedVendeg = vendegConverter.toEntity(vendegDto);

        Vendeg vendeg = vendegRepository.getById(id);

        vendeg.setBecenev(updatedVendeg.getBecenev());
        vendeg.setMajerosseg(updatedVendeg.getMajerosseg());
        vendeg.setBicepszmeret(updatedVendeg.getBicepszmeret());

        return vendegRepository.save(vendeg);
    }

    public List<Vendeg> findAll() {
        return vendegRepository.findAll();
    }

    public Vendeg findById(Long id) {
        return vendegRepository.getById(id);
    }

    public List<VendegFogyasztasSzerintDto> getVendegekByElfogyasztottMennyiseg() {
        return vendegRepository.findAll().stream()
                .map(vendeg -> {
                    int elfogyasztottMennyisegByVendeg = getElfogyasztottMennyisegByVendeg(vendeg);

                    return vendegConverter.toVendegFogyasztasSzerintDto(vendeg.getBecenev(), elfogyasztottMennyisegByVendeg);
                })
                .collect(Collectors.toList());
    }

    protected int getElfogyasztottMennyisegByVendeg(Vendeg vendeg) {
        return vendeg.getKocsmazasList().stream()
                .flatMap(kocsmazas -> kocsmazas.getFogyasztasLista().stream())
                .mapToInt(Fogyasztas::getElfogyasztottMennyiseg)
                .sum();
    }
}
