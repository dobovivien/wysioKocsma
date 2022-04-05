package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.repository.KocsmazasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KocsmazasService {

    @Autowired
    private KocsmazasRepository kocsmazasRepository;

    @Autowired
    private VendegService vendegService;

    public long startKocsmazas(long vendegId) throws ResourceNotFoundException {
        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setMettol(LocalDateTime.now());
        kocsmazas.setVendeg(vendegService.findById(vendegId));
        return kocsmazasRepository.save(kocsmazas).getId();
    }

    public Kocsmazas getBefejezetlenKocsmazasByVendegId(long vendegId) throws ResourceNotFoundException {
        Vendeg vendeg = vendegService.findById(vendegId);
        List<Kocsmazas> kocsmazasList = kocsmazasRepository.findAllByVendeg(vendeg);
        Optional<Kocsmazas> befejezetlenKocsmazas = kocsmazasList.stream().filter(kocsmazas -> kocsmazas.getMeddig() == null).findFirst();
        return befejezetlenKocsmazas.orElse(null);
    }

    public long finishKocsmazas(long vendegId) throws ResourceNotFoundException {
        Kocsmazas befejezetlenKocsmazas = getBefejezetlenKocsmazasByVendegId(vendegId);
        if (befejezetlenKocsmazas != null) {
            befejezetlenKocsmazas.setMeddig(LocalDateTime.now());
            return kocsmazasRepository.save(befejezetlenKocsmazas).getId();
        } else {
            //todo exception
            return 0;
        }
    }

    public void addToDetox(long vendegId) throws ResourceNotFoundException {
        Kocsmazas kocsmazas = getBefejezetlenKocsmazasByVendegId(vendegId);
        if (kocsmazas != null) {
            kocsmazas.setDetoxbaKerult(true);
            kocsmazasRepository.save(kocsmazas);
            finishKocsmazas(vendegId);
        } else {
            //todo exception
        }
    }
}
