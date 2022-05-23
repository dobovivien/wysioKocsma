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

    public long startKocsmazas(Long vendegId) throws KocsmazasException {
        Vendeg vendeg;

        try {
            vendeg = vendegService.findById(vendegId);

        } catch (Exception e) {
            throw new KocsmazasException(ExceptionMessage.NINCS_VENDEG.getMessage());
        }

        try {
            Kocsmazas kocsmazas = new Kocsmazas();
            kocsmazas.setMettol(LocalDateTime.now());
            kocsmazas.setVendeg(vendeg);

            return kocsmazasRepository.save(kocsmazas).getId();

        } catch (Exception e) {
            throw new KocsmazasException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public Kocsmazas getBefejezetlenKocsmazasByVendegId(Long vendegId) throws KocsmazasException {
        List<Kocsmazas> kocsmazasList;

        try {
            kocsmazasList = kocsmazasRepository.findAllByVendegId(vendegId);

        } catch (Exception e) {
            throw new KocsmazasException(ExceptionMessage.NINCS_VENDEG.getMessage() + vendegId);
        }

        return kocsmazasList.stream()
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
            throw new KocsmazasException(ExceptionMessage.NINCS_VENDEG.getMessage() + vendegId);
        }
    }

    public void addToDetox(Long vendegId) throws KocsmazasException {
        Kocsmazas kocsmazas = getBefejezetlenKocsmazasByVendegId(vendegId);
        if (kocsmazas != null) {
            kocsmazas.setDetoxbaKerult(true);

            try {
                kocsmazasRepository.save(kocsmazas);

            } catch (Exception e) {
                throw new KocsmazasException(ExceptionMessage.SIKERTELEN.getMessage());
            }
            finishKocsmazas(vendegId);

        } else {
            throw new KocsmazasException(ExceptionMessage.NINCS_VENDEG.getMessage() + vendegId);
        }
    }

    public boolean vendegIsDetoxos(Long vendegId) {
        int allKocsmazasByVendegDetoxban = kocsmazasRepository.getKocsmazasCountByVendegDetoxban(vendegId);

        return allKocsmazasByVendegDetoxban > MAX_DETOX;
    }

    public double getHetiAtlagosKocsmazasSzama(Long vendegId) throws KocsmazasException {
        int kocsmazasokSzama;

        try {
            kocsmazasokSzama = kocsmazasRepository.findAllByVendegId(vendegId).size();

        } catch (Exception e) {
            throw new KocsmazasException(ExceptionMessage.SIKERTELEN.getMessage());
        }

        KocsmazasIdotartam kocsmazasIdotartam;
        try {
            kocsmazasIdotartam = kocsmazasRepository.findElsoEsUtolsoKocsmazasByVendegId(vendegId);

        } catch (Exception e) {
            throw new KocsmazasException(ExceptionMessage.NINCS_KOCSMAZAS.getMessage() + vendegId);
        }

        if (kocsmazasIdotartam.getMettol() != null && kocsmazasIdotartam.getMeddig() != null) {
            int napokSzama = (int) Duration.between(kocsmazasIdotartam.getMettol(), kocsmazasIdotartam.getMeddig()).toDays() + 1;
            int hetekSzama = (int) Math.ceil(napokSzama / 7.0);

            return (double) kocsmazasokSzama / hetekSzama;
        }
        return 0.0;
    }

    private double getVendegAtlagosFogyasztasAdatok(Long vendegId) throws KocsmazasException {
        List<FogyasztasAdatok> fogyasztasAdatok;

        try {
            fogyasztasAdatok = fogyasztasRepository.getFogyasztasAdatok(vendegId);

        } catch (Exception e) {
            throw new KocsmazasException(ExceptionMessage.NINCS_FOGYASZTAS.getMessage());
        }

        int fogyasztasSuly = fogyasztasAdatok.stream()
                .mapToInt(fogyasztas -> fogyasztas.getAdagMennyisege() * fogyasztas.getElfogyasztottMennyiseg()
                        * fogyasztas.getAlkoholTartalom())
                .sum();

        return (double) fogyasztasSuly / fogyasztasAdatok.size();
    }

    public boolean isAlkoholista(Long vendegId) throws KocsmazasException {
        return vendegIsDetoxos(vendegId)
                && getHetiAtlagosKocsmazasSzama(vendegId) > MAX_KOCSMAZAS
                && getVendegAtlagosFogyasztasAdatok(vendegId) > MAX_FOGYASZTAS;
    }

    public List<KocsmazasDto> isAlkoholistaWithCriteriaBuilder(Long vendegId) throws KocsmazasException {
        try {
            return kocsmazasConverter.toDtoList(
                    kocsmazasRepository.isAlkoholistaWithCriteriaBuilder(vendegId));

        } catch (Exception e) {
            throw new KocsmazasException(ExceptionMessage.NINCS_VENDEG.getMessage());
        }
    }
}
