package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import hu.wysio.training.vivi.wysioKocsma.repository.BunyoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BunyoService {

    @Autowired
    private BunyoRepository bunyoRepository;

    public Bunyo createBunyo(Bunyo bunyoAdat) {
        return bunyoRepository.save(bunyoAdat);
    }

    public Bunyo updateBunyo(long id, Bunyo bunyoAdat) throws ResourceNotFoundException {
        Bunyo bunyo = bunyoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen bunyo az alabbi id-val: " + id));

        bunyo.setMettol(bunyoAdat.getMettol());
        bunyo.setMeddig(bunyoAdat.getMeddig());
        bunyo.setResztvevok(bunyoAdat.getResztvevok());
        bunyo.setNyertes(bunyoAdat.getNyertes());

        return bunyoRepository.save(bunyo);
    }

    public List<Bunyo> findAll() {
        return bunyoRepository.findAll();
    }

    public Bunyo findById(long id) throws ResourceNotFoundException {
        return bunyoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen bunyo az alabbi id-val: " + id));
    }

    public void deleteBunyo(Bunyo bunyoAdat) throws ResourceNotFoundException {
        try {
            bunyoRepository.delete(bunyoAdat);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Nincs ilyen bunyo az alabbi id-val: " + bunyoAdat.getId());
        }
    }
}
