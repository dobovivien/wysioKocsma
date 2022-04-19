package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.converter.KocsmazasConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.FogyasztasAdatok;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.KocsmazasIdotartam;
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

    private int maxDetox = 5;
    private int maxKocsmazas = 4;
    private int maxFogyasztas = 2000;

    @Autowired
    private KocsmazasRepository kocsmazasRepository;

    @Autowired
    private VendegService vendegService;

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private KocsmazasConverter kocsmazasConverter;

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
        int allKocsmazasByVendegDetoxban = kocsmazasRepository.getKocsmazasCountByVendegDetoxban(vendegId);
        if (allKocsmazasByVendegDetoxban > maxDetox) {
            return true;
        }
        return false;
    }

    public double getHetiAtlagosKocsmazasSzama(Long vendegId) {
        int napokSzama = 0;
        int kocsmazasokSzama = kocsmazasRepository.findAllByVendeg_Id(vendegId).size();
        KocsmazasIdotartam kocsmazasIdotartam = kocsmazasRepository.findElsoEsUtolsoKocsmazasByVendegId(vendegId);
        if (kocsmazasIdotartam.getMettol() != null && kocsmazasIdotartam.getMeddig() != null) {
            napokSzama = (int) Duration.between(kocsmazasIdotartam.getMettol(), kocsmazasIdotartam.getMeddig()).toDays() + 1;
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
        return vendegIsDetoxos(vendegId) && getHetiAtlagosKocsmazasSzama(vendegId) > maxKocsmazas && getVendegAtlagosFogyasztasAdatok(vendegId) > maxFogyasztas;
    }

    public List<KocsmazasDto> isAlkoholistaWithCriteriaBuilder(Long vendegId) {
        return kocsmazasConverter.convertKocsmazasListToKocsmazasDtoList(
                kocsmazasRepository.isAlkoholistaWithCriteriaBuilder(vendegId));
    }
}