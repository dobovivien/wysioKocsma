package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.KocsmazasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysiokocsma.exception.KocsmazasException;
import hu.wysio.training.vivi.wysiokocsma.exception.VendegException;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.model.*;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.KocsmazasRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KocsmazasService {

    private static final int MAX_DETOX = 5;
    private static final int MAX_KOCSMAZAS = 4;
    private static final int MAX_FOGYASZTAS = 2000;

    @Autowired
    private KocsmazasRepository kocsmazasRepository;

    @Autowired
    private VendegService vendegService;

    @Autowired
    private VendegRepository vendegRepository;

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private KocsmazasConverter kocsmazasConverter;

    public long startKocsmazas(Long vendegId) throws WysioKocsmaException {
        Vendeg vendeg = vendegService.getById(vendegId);

        Optional<Kocsmazas> kocsmazasnakNincsVege = vendeg.getKocsmazasList().stream()
                .filter(kocsmazas -> kocsmazas.getMeddig() == null)
                .findFirst();

        if (kocsmazasnakNincsVege.isPresent()) {
            throw new KocsmazasException(ExceptionMessage.NEM_KEZDHETO_KOCSMAZAS);
        }

        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setMettol(LocalDateTime.now());
        kocsmazas.setVendeg(vendeg);

        return kocsmazasRepository.save(kocsmazas).getId();
    }

    public Optional<Kocsmazas> findBefejezetlenKocsmazasByVendegId(Long vendegId) throws WysioKocsmaException {
        if (vendegRepository.findById(vendegId).isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        return kocsmazasRepository.findAllByVendegId(vendegId).stream()
                .filter(kocsmazas -> kocsmazas.getMeddig() == null)
                .findFirst();
    }

    public List<Kocsmazas> getAllBefejezetlenKocsmazas() {
        List<Kocsmazas> kocsmazasList = kocsmazasRepository.findAll();
        return kocsmazasList.stream()
                .filter(kocsmazas -> kocsmazas.getMeddig() == null)
                .collect(Collectors.toList());
    }

    public long finishKocsmazas(Long vendegId) throws WysioKocsmaException {
        Optional<Kocsmazas> befejezetlenKocsmazasOptional = findBefejezetlenKocsmazasByVendegId(vendegId);

        if (befejezetlenKocsmazasOptional.isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        Kocsmazas befejezetlenKocsmazas = befejezetlenKocsmazasOptional.get();

        befejezetlenKocsmazas.setMeddig(LocalDateTime.now());

        return kocsmazasRepository.save(befejezetlenKocsmazas).getId();
    }

    public void addToDetox(Long vendegId) throws WysioKocsmaException {
        Optional<Kocsmazas> kocsmazasOptional = findBefejezetlenKocsmazasByVendegId(vendegId);

        if (kocsmazasOptional.isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        Kocsmazas kocsmazas = kocsmazasOptional.get();

        kocsmazas.setDetoxbaKerult(true);

        kocsmazasRepository.save(kocsmazas);

        finishKocsmazas(vendegId);
    }

    public boolean vendegIsDetoxos(Long vendegId) throws WysioKocsmaException {
        if (vendegRepository.findById(vendegId).isEmpty()) {
            throw new KocsmazasException(ExceptionMessage.NINCS_VENDEG);
        }

        return kocsmazasRepository.getKocsmazasCountByVendegDetoxban(vendegId) > MAX_DETOX;
    }

    public double getHetiAtlagosKocsmazasSzama(Long vendegId) throws WysioKocsmaException {
        if (vendegRepository.findById(vendegId).isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        int kocsmazasokSzama = kocsmazasRepository.findAllByVendegId(vendegId).size();

        KocsmazasIdotartam kocsmazasIdotartam = kocsmazasRepository.findElsoEsUtolsoKocsmazasByVendegId(vendegId);

        if (kocsmazasIdotartam.getMettol() != null && kocsmazasIdotartam.getMeddig() != null) {
            int napokSzama = (int) Duration.between(kocsmazasIdotartam.getMettol(), kocsmazasIdotartam.getMeddig()).toDays() + 1;
            int hetekSzama = (int) Math.ceil(napokSzama / 7.0);

            return (double) kocsmazasokSzama / hetekSzama;
        }
        return 0.0;
    }

    private double getVendegAtlagosFogyasztasAdatok(Long vendegId) throws WysioKocsmaException {
        if (vendegRepository.findById(vendegId).isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        List<FogyasztasAdatok> fogyasztasAdatok = fogyasztasRepository.getFogyasztasAdatok(vendegId);

        int fogyasztasSuly = fogyasztasAdatok.stream()
                .mapToInt(fogyasztas -> fogyasztas.getAdagMennyisege() * fogyasztas.getElfogyasztottMennyiseg()
                        * fogyasztas.getAlkoholTartalom())
                .sum();

        return (double) fogyasztasSuly / fogyasztasAdatok.size();
    }

    public boolean isAlkoholista(Long vendegId) throws WysioKocsmaException {
        if (vendegRepository.findById(vendegId).isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }
        
        return vendegIsDetoxos(vendegId)
                && getHetiAtlagosKocsmazasSzama(vendegId) > MAX_KOCSMAZAS
                && getVendegAtlagosFogyasztasAdatok(vendegId) > MAX_FOGYASZTAS;
    }

    public List<KocsmazasDto> isAlkoholistaWithCriteriaBuilder(Long vendegId) throws VendegException {
        if (vendegRepository.findById(vendegId).isEmpty()) {
            throw new VendegException(ExceptionMessage.NINCS_VENDEG);
        }

        return kocsmazasConverter.toDtoList(
                kocsmazasRepository.isAlkoholistaWithCriteriaBuilder(vendegId));
    }
}
