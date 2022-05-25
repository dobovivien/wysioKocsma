package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.KocsmazasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysiokocsma.exception.KocsmazasException;
import hu.wysio.training.vivi.wysiokocsma.model.*;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.KocsmazasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private KocsmazasConverter kocsmazasConverter;

    public long startKocsmazas(Long vendegId) {
        Vendeg vendeg = vendegService.findById(vendegId);

        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setMettol(LocalDateTime.now());
        kocsmazas.setVendeg(vendeg);

        return kocsmazasRepository.save(kocsmazas).getId();
    }

    public Kocsmazas getBefejezetlenKocsmazasByVendegId(Long vendegId) {
        return kocsmazasRepository.findAllByVendegId(vendegId).stream()
                .filter(kocsmazas -> kocsmazas.getMeddig() == null)
                .findFirst()
                .orElse(null);
    }

    public List<Kocsmazas> getAllBefejezetlenKocsmazas() {
        List<Kocsmazas> kocsmazasList = kocsmazasRepository.findAll();
        return kocsmazasList.stream()
                .filter(kocsmazas -> kocsmazas.getMeddig() == null)
                .collect(Collectors.toList());
    }

    public long finishKocsmazas(Long vendegId) throws KocsmazasException {
        Kocsmazas befejezetlenKocsmazas = getBefejezetlenKocsmazasByVendegId(vendegId);

        if (befejezetlenKocsmazas != null) {
            befejezetlenKocsmazas.setMeddig(LocalDateTime.now());

            return kocsmazasRepository.save(befejezetlenKocsmazas).getId();

        } else {
            throw new KocsmazasException(ExceptionMessage.NINCS_KOCSMAZAS);
        }
    }

    public void addToDetox(Long vendegId) throws KocsmazasException {
        Kocsmazas kocsmazas = getBefejezetlenKocsmazasByVendegId(vendegId);

        if (kocsmazas != null) {
            kocsmazas.setDetoxbaKerult(true);

            kocsmazasRepository.save(kocsmazas);

            finishKocsmazas(vendegId);

        } else {
            throw new KocsmazasException(ExceptionMessage.NINCS_VENDEG);
        }
    }

    public boolean vendegIsDetoxos(Long vendegId) {
        return kocsmazasRepository.getKocsmazasCountByVendegDetoxban(vendegId) > MAX_DETOX;
    }

    public double getHetiAtlagosKocsmazasSzama(Long vendegId) {
        int kocsmazasokSzama = kocsmazasRepository.findAllByVendegId(vendegId).size();

        KocsmazasIdotartam kocsmazasIdotartam = kocsmazasRepository.findElsoEsUtolsoKocsmazasByVendegId(vendegId);

        if (kocsmazasIdotartam.getMettol() != null && kocsmazasIdotartam.getMeddig() != null) {
            int napokSzama = (int) Duration.between(kocsmazasIdotartam.getMettol(), kocsmazasIdotartam.getMeddig()).toDays() + 1;
            int hetekSzama = (int) Math.ceil(napokSzama / 7.0);

            return (double) kocsmazasokSzama / hetekSzama;
        }
        return 0.0;
    }

    private double getVendegAtlagosFogyasztasAdatok(Long vendegId) {
        List<FogyasztasAdatok> fogyasztasAdatok = fogyasztasRepository.getFogyasztasAdatok(vendegId);

        int fogyasztasSuly = fogyasztasAdatok.stream()
                .mapToInt(fogyasztas -> fogyasztas.getAdagMennyisege() * fogyasztas.getElfogyasztottMennyiseg()
                        * fogyasztas.getAlkoholTartalom())
                .sum();

        return (double) fogyasztasSuly / fogyasztasAdatok.size();
    }

    public boolean isAlkoholista(Long vendegId) {
        return vendegIsDetoxos(vendegId)
                && getHetiAtlagosKocsmazasSzama(vendegId) > MAX_KOCSMAZAS
                && getVendegAtlagosFogyasztasAdatok(vendegId) > MAX_FOGYASZTAS;
    }

    public List<KocsmazasDto> isAlkoholistaWithCriteriaBuilder(Long vendegId) {
        return kocsmazasConverter.toDtoList(
                kocsmazasRepository.isAlkoholistaWithCriteriaBuilder(vendegId));
    }
}
