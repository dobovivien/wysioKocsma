package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.exception.VendegException;
import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
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

    public long createVendeg(VendegDto vendegDto) throws VendegException {
        Vendeg vendegToSave = vendegConverter.toEntity(vendegDto);

        try {
            Vendeg vendeg = vendegRepository.save(vendegToSave);

            return vendeg.getId();

        } catch (IllegalArgumentException e) {
            throw new VendegException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public Vendeg updateVendeg(Long id, VendegDto vendegDto) throws VendegException {
        Vendeg vendeg;
        Vendeg updatedVendeg = vendegConverter.toEntity(vendegDto);

        try {
            vendeg = vendegRepository.getById(id);

        } catch (IllegalArgumentException e) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG.getMessage() + id);
        }

        try {
            vendeg.setBecenev(updatedVendeg.getBecenev());
            vendeg.setMajerosseg(updatedVendeg.getMajerosseg());
            vendeg.setBicepszmeret(updatedVendeg.getBicepszmeret());

            return vendegRepository.save(vendeg);

        } catch (IllegalArgumentException e) {
            throw new VendegException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public List<Vendeg> findAll() throws VendegException {
        try {
            return vendegRepository.findAll();

        } catch (Exception e) {
            throw new VendegException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public Vendeg findById(Long id) throws VendegException {
        try {
            return vendegRepository.getById(id);

        } catch (Exception e) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG.getMessage() + id);
        }
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
