package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import hu.wysio.training.vivi.wysioKocsma.repository.ItalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItalService {

    @Autowired
    private ItalRepository italRepository;

    public Ital createItal(Ital italAdat) {
        return italRepository.save(italAdat);
    }

    public Ital updateItal(long id, Ital italAdat) throws ResourceNotFoundException {
        Ital ital = italRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen ital az alabbi id-val: " + id));

        ital.setNev(italAdat.getNev());
        ital.setAdagMennyisege(italAdat.getAdagMennyisege());
        ital.setAlkoholTartalom(italAdat.getAlkoholTartalom());

        return italRepository.save(ital);
    }

    public List<Ital> findAll() {
        return italRepository.findAll();
    }

    public Ital findById(long id) throws ResourceNotFoundException {
        return italRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen ital az alabbi id-val: " + id));
    }

    public void deleteItal(Ital italAdat) throws ResourceNotFoundException {
        try {
            italRepository.delete(italAdat);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Nincs ilyen ital az alabbi id-val: " + italAdat.getId());
        }
    }
}
