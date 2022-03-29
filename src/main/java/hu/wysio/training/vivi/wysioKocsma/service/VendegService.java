package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendegService {

    @Autowired
    private VendegRepository vendegRepository;

    public Vendeg createVendeg(Vendeg vendegAdat) {
        return vendegRepository.save(vendegAdat);
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

    public void deleteVendeg(Vendeg vendegAdat) throws ResourceNotFoundException {
        try {
            vendegRepository.delete(vendegAdat);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Nincs ilyen vendeg az alabbi id-val: " + vendegAdat.getId());
        }
    }
}
