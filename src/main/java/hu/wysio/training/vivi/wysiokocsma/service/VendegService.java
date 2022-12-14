package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.exception.VendegException;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Vendeg updateVendeg(Long id, VendegDto vendegDto) throws WysioKocsmaException {
        Optional<Vendeg> vendegOptional = vendegRepository.findById(id);

        if (vendegOptional.isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        Vendeg vendeg = vendegOptional.get();

        vendeg.setBecenev(vendegDto.getNev());
        vendeg.setMajerosseg(vendegDto.getMajerosseg());
        vendeg.setBicepszmeret(vendegDto.getBicepszmeret());

        return vendegRepository.save(vendeg);
    }

    public List<Vendeg> findAll() {
        return vendegRepository.findAll();
    }

    public Vendeg getById(Long id) throws WysioKocsmaException {
        Optional<Vendeg> vendegOptional = vendegRepository.findById(id);

        if (vendegOptional.isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        return vendegOptional.get();
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
