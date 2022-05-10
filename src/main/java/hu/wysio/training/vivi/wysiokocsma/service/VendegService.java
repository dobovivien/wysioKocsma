package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.exception.VendegException;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendegService {

    private static final String NINCS_VENDEG = "Nincs ilyen vendég az alabbi id-val: ";
    private static final String SIKERTELEN = "Sikertelen művelet.";

    @Autowired
    private VendegRepository vendegRepository;

    @Autowired
    private VendegConverter vendegConverter;

    public long createVendeg(VendegDto vendegDto) throws VendegException {
        try {
            Vendeg vendeg = vendegRepository.save(vendegConverter.convertDtoToVendeg(vendegDto));

            return vendeg.getId();

        } catch (IllegalArgumentException e) {
            throw new VendegException(SIKERTELEN);
        }
    }

    public Vendeg updateVendeg(long id, Vendeg vendegAdat) throws VendegException {
        Vendeg vendeg;

        if (vendegRepository.findById(id).isPresent()) {
            try {
                vendeg = vendegRepository.getById(id);

            } catch (IllegalArgumentException e) {
                throw new VendegException(NINCS_VENDEG + id);
            }

            try {
                vendeg.setBecenev(vendegAdat.getBecenev());
                vendeg.setMajerosseg(vendegAdat.getMajerosseg());
                vendeg.setBicepszmeret(vendegAdat.getBicepszmeret());

                return vendegRepository.save(vendeg);

            } catch (IllegalArgumentException e) {
                throw new VendegException(SIKERTELEN);
            }
        }
        throw new VendegException(NINCS_VENDEG + id);
    }

    public List<Vendeg> findAll() throws VendegException {
        try {
            return vendegRepository.findAll();

        } catch (Exception e) {
            throw new VendegException(SIKERTELEN);
        }
    }

    public Vendeg findById(long id) throws VendegException {
        if (vendegRepository.findById(id).isPresent()) {
            try {
                return vendegRepository.getById(id);

            } catch (Exception e) {
                throw new VendegException(NINCS_VENDEG + id);
            }
        }
        throw new VendegException(NINCS_VENDEG + id);
    }

    public List<VendegFogyasztasSzerintDto> getVendegekByElfogyasztottMennyiseg() {
        return vendegRepository.findAll().stream()
                .map(vendeg -> vendegConverter.convertVendegToVFSZDto(vendeg, getElfogyasztottMennyisegByVendeg(vendeg)))
                .collect(Collectors.toList());
    }

    public long getElfogyasztottMennyisegByVendeg(Vendeg vendeg) {
        return vendeg.getKocsmazasList().stream()
                .flatMap(kocsmazas -> kocsmazas.getFogyasztasLista().stream())
                .mapToInt(Fogyasztas::getElfogyasztottMennyiseg)
                .sum();
    }
}
