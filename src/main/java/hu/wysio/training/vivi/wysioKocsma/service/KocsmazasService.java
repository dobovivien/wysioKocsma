package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.FogyasztasAdatok;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.repository.FogyasztasRepository;
import hu.wysio.training.vivi.wysioKocsma.repository.KocsmazasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KocsmazasService {

    @Autowired
    private KocsmazasRepository kocsmazasRepository;

    @Autowired
    private VendegService vendegService;

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    public long startKocsmazas(long vendegId) throws ResourceNotFoundException {
        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setMettol(LocalDateTime.now());
        kocsmazas.setVendeg(vendegService.findById(vendegId));
        return kocsmazasRepository.save(kocsmazas).getId();
    }

    public Kocsmazas getBefejezetlenKocsmazasByVendegId(long vendegId) throws ResourceNotFoundException {
        List<Kocsmazas> kocsmazasList = kocsmazasRepository.findAllByVendeg_Id(vendegId);
        Optional<Kocsmazas> befejezetlenKocsmazas = kocsmazasList.stream().filter(kocsmazas -> kocsmazas.getMeddig() == null).findFirst();
        return befejezetlenKocsmazas.orElse(null);
    }

    public List<Kocsmazas> getAllBefejezetlenKocsmazas() {
        List<Kocsmazas> kocsmazasList = kocsmazasRepository.findAll();
        List<Kocsmazas> allBefejezetlenKocsmazas = kocsmazasList.stream().filter(kocsmazas -> kocsmazas.getMeddig() == null).toList();
        return allBefejezetlenKocsmazas;
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

    public boolean vendegIsDetoxos(Long vendegId) {
        int allKocsmazasByVendegDetoxban = kocsmazasRepository.findAllKocsmazasByVendegDetoxban(vendegId);
        if (allKocsmazasByVendegDetoxban > 5) {
            return true;
        }
        return false;
    }

    public double getHetiAtlagosKocsmazasSzama(Long vendegId) {
        int napokSzama = 0;
        int kocsmazasokSzama = kocsmazasRepository.findAllByVendeg_Id(vendegId).size();
        LocalDateTime elsoKocsmazas = kocsmazasRepository.findElsoKocsmazas(vendegId);
        LocalDateTime utolsoKocsmazas = kocsmazasRepository.findUtolsoKocsmazas(vendegId);
        if (elsoKocsmazas != null && utolsoKocsmazas != null) {
            napokSzama = (int) Duration.between(elsoKocsmazas, utolsoKocsmazas).toDays() + 1;
            int hetekSzama = (int) Math.ceil(napokSzama / 7.0);
            return (double) kocsmazasokSzama / hetekSzama;
        }
        return 0.0;
        //todo exception
    }

    public double getVendegAtlagosFogyasztasAdatok(Long vendegId) {
        int fogyasztasSuly = 0;
        List<FogyasztasAdatok> fogyasztasAdatok = fogyasztasRepository.getFogyasztasAdatok(vendegId);
        for (FogyasztasAdatok fa : fogyasztasAdatok) {
            fogyasztasSuly += (fa.getAdagMennyisege() * fa.getElfogyasztottMennyiseg() * fa.getAlkoholTartalom());
        }
        return (double) fogyasztasSuly / fogyasztasAdatok.size();
    }

    public boolean isAlkoholista(Long vendegId) {
        if (vendegIsDetoxos(vendegId) && getHetiAtlagosKocsmazasSzama(vendegId) > 4 && getVendegAtlagosFogyasztasAdatok(vendegId) > 2000) {
            return true;
        }
        return false;
    }

}