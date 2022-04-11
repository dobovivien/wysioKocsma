package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendegService {

    @Autowired
    private VendegRepository vendegRepository;

    @Autowired
    private VendegConverter vendegConverter;

    public long createVendeg(VendegDto vendegDto) {
        Vendeg vendeg = vendegRepository.save(vendegConverter.convertDtoToVendeg(vendegDto));
        return vendeg.getId();
    }

    public Vendeg updateVendeg(long id, Vendeg vendegAdat) throws ResourceNotFoundException {
        Vendeg vendeg = vendegRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen vendeg az alabbi id-val: " + id));

        vendeg.setBecenev(vendegAdat.getBecenev());
        vendeg.setMajerosseg(vendegAdat.getMajerosseg());
        vendeg.setBicepszmeret(vendegAdat.getBicepszmeret());

        return vendegRepository.save(vendeg);
    }

    public List<Vendeg> findAll() {
        return vendegRepository.findAll();
    }

    public Vendeg findById(long id) throws ResourceNotFoundException {
        return vendegRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen vendeg az alabbi id-val: " + id));
    }

    //Vendeg -> kocsmazasList -> Kocsmazas -> fogyasztasList -> Fogyasztas -> elfogyasztottMennyiseg
    public List<VendegFogyasztasSzerintDto> getVendegekByElfogyasztottMennyiseg() {
        List<VendegFogyasztasSzerintDto> vendegFogyasztasSzerintDtoList = new ArrayList<>();
        List<Vendeg> vendegList = vendegRepository.findAll();
        for (Vendeg vendeg : vendegList) {
            long fogyasztasByVendegId = getElfogyasztottMennyisegByVendegId(vendeg.getId());
            vendegFogyasztasSzerintDtoList.add(vendegConverter.convertVendegToVFSZDto(vendeg, fogyasztasByVendegId));
        }
        return vendegFogyasztasSzerintDtoList;
    }

    public long getElfogyasztottMennyisegByVendegId(Long vendegId) {
        Optional<Vendeg> vendeg = vendegRepository.findById(vendegId);
        if (vendeg.isPresent()) {
            return vendeg.get().getKocsmazasList().stream()
                    .flatMap(kocsmazas -> kocsmazas.getFogyasztasLista().stream())
                    .mapToInt(Fogyasztas::getElfogyasztottMennyiseg).sum();
        }
        return 0;
        //todo: exception
    }
}
