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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try {
            vendeg = vendegRepository.findById(id).get();
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

    public List<Vendeg> findAll() throws VendegException {
        try {
            return vendegRepository.findAll();
        } catch (Exception e) {
            throw new VendegException(SIKERTELEN);
        }
    }

    public Vendeg findById(long id) throws VendegException {
        try {
            return vendegRepository.findById(id).get();
        } catch (Exception e) {
            throw new VendegException(NINCS_VENDEG + id);
        }
    }

    //Vendeg -> kocsmazasList -> Kocsmazas -> fogyasztasList -> Fogyasztas -> elfogyasztottMennyiseg
    public List<VendegFogyasztasSzerintDto> getVendegekByElfogyasztottMennyiseg() throws VendegException {
        List<VendegFogyasztasSzerintDto> vendegFogyasztasSzerintDtoList = new ArrayList<>();
        List<Vendeg> vendegList = vendegRepository.findAll();
        for (Vendeg vendeg : vendegList) {
            long fogyasztasByVendegId = getElfogyasztottMennyisegByVendegId(vendeg.getId());
            vendegFogyasztasSzerintDtoList.add(vendegConverter.convertVendegToVFSZDto(vendeg, fogyasztasByVendegId));
        }
        return vendegFogyasztasSzerintDtoList;
    }

    public long getElfogyasztottMennyisegByVendegId(Long vendegId) throws VendegException {
        Optional<Vendeg> vendeg;
        try {
            vendeg = vendegRepository.findById(vendegId);
        } catch (IllegalArgumentException e) {
            throw new VendegException(NINCS_VENDEG);
        }
        return vendeg.map(value -> value.getKocsmazasList().stream()
                .flatMap(kocsmazas -> kocsmazas.getFogyasztasLista().stream())
                .mapToInt(Fogyasztas::getElfogyasztottMennyiseg).sum()).orElse(0);
    }
}
