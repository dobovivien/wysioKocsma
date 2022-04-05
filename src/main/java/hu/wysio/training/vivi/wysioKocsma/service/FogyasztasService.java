package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.repository.FogyasztasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FogyasztasService {

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private FogyasztasConverter fogyasztasConverter;

    public long createFogyasztas(FogyasztasDto fogyasztasDto) {
        Fogyasztas fogyasztas = fogyasztasRepository.save(fogyasztasConverter.convertDtoToFogyasztas(fogyasztasDto));
        return fogyasztas.getId();
    }

    public Fogyasztas updateFogyasztas(long id, Fogyasztas fogyasztasAdat) throws ResourceNotFoundException {
        Fogyasztas fogyasztas = fogyasztasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen fogyasztas az alabbi id-val: " + id));

        fogyasztas.setItal(fogyasztasAdat.getItal());
        fogyasztas.setElfogyasztottMennyiseg(fogyasztasAdat.getElfogyasztottMennyiseg());

        return fogyasztasRepository.save(fogyasztas);
    }

    public List<Fogyasztas> findAll() {
        return fogyasztasRepository.findAll();
    }

    public Fogyasztas findById(long id) throws ResourceNotFoundException {
        return fogyasztasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen fogyasztas az alabbi id-val: " + id));
    }

    public void deleteFogyasztas(Fogyasztas fogyasztasAdat) throws ResourceNotFoundException {
        try {
            fogyasztasRepository.delete(fogyasztasAdat);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Nincs ilyen fogyasztas az alabbi id-val: " + fogyasztasAdat.getId());
        }
    }
}
