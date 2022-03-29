package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.repository.KocsmazasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KocsmazasService {

    @Autowired
    private KocsmazasRepository kocsmazasRepository;

    public Kocsmazas createKocsmazas(Kocsmazas kocsmazasAdat) {
        return kocsmazasRepository.save(kocsmazasAdat);
    }

    public Kocsmazas updateKocsmazas(long id, Kocsmazas kocsmazasAdat) throws ResourceNotFoundException {
        Kocsmazas kocsmazas = kocsmazasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen kocsmazas az alabbi id-val: " + id));

        kocsmazas.setMettol(kocsmazasAdat.getMettol());
        kocsmazas.setMeddig(kocsmazasAdat.getMeddig());
        kocsmazas.setFogyasztasLista(kocsmazasAdat.getFogyasztasLista());
        kocsmazas.setDetoxbaKerult(kocsmazasAdat.isDetoxbaKerult());

        return kocsmazasRepository.save(kocsmazas);
    }

    public List<Kocsmazas> findAll() {
        return kocsmazasRepository.findAll();
    }

    public Kocsmazas findById(long id) throws ResourceNotFoundException {
        return kocsmazasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen kocsmazas az alabbi id-val: " + id));
    }

    public void deleteKocsmazas(Kocsmazas kocsmazasAdat) throws ResourceNotFoundException {
        try {
            kocsmazasRepository.delete(kocsmazasAdat);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Nincs ilyen kocsmazas az alabbi id-val: " + kocsmazasAdat.getId());
        }
    }
}
