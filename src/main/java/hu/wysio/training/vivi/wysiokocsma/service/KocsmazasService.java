package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.KocsmazasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysiokocsma.exception.KocsmazasException;
import hu.wysio.training.vivi.wysiokocsma.model.FogyasztasAdatok;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.KocsmazasIdotartam;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.KocsmazasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KocsmazasService {

    private static final String NINCS_KOCSMAZAS = "Nincs ilyen kocsmázás az alabbi vendég id-val: ";
    private static final String NINCS_VENDEG = "Nincs ilyen vendég az alabbi id-val: ";
    private static final String NINCS_FOGYASZTAS = "Nincs ilyen fogyasztás az alabbi vendég id-val: ";
    private static final String SIKERTELEN = "Sikertelen művelet.";
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

    public long startKocsmazas(long vendegId) throws KocsmazasException {
        try {
            Kocsmazas kocsmazas = new Kocsmazas();
            kocsmazas.setMettol(LocalDateTime.now());
            Vendeg vendeg;
            try {
                vendeg = vendegService.findById(vendegId);
            } catch (Exception e) {
                throw new KocsmazasException(NINCS_VENDEG);
            }
            kocsmazas.setVendeg(vendeg);
            return kocsmazasRepository.save(kocsmazas).getId();
        } catch (Exception e) {
            throw new KocsmazasException(SIKERTELEN);
        }

    }

    public Kocsmazas getBefejezetlenKocsmazasByVendegId(long vendegId) throws KocsmazasException {
        List<Kocsmazas> kocsmazasList;
        try {
            kocsmazasList = kocsmazasRepository.findAllByVendeg_Id(vendegId);
        } catch (Exception e) {
            throw new KocsmazasException(NINCS_VENDEG + vendegId);
        }
        Optional<Kocsmazas> befejezetlenKocsmazas = kocsmazasList.stream().filter(kocsmazas -> kocsmazas.getMeddig() == null).findFirst();
        return befejezetlenKocsmazas.orElse(null);
    }

    public List<Kocsmazas> getAllBefejezetlenKocsmazas() {
        List<Kocsmazas> kocsmazasList = kocsmazasRepository.findAll();
        return kocsmazasList.stream().filter(kocsmazas -> kocsmazas.getMeddig() == null).collect(Collectors.toList());
    }

    public long finishKocsmazas(long vendegId) throws KocsmazasException {
        Kocsmazas befejezetlenKocsmazas = getBefejezetlenKocsmazasByVendegId(vendegId);
        if (befejezetlenKocsmazas != null) {
            befejezetlenKocsmazas.setMeddig(LocalDateTime.now());
            return kocsmazasRepository.save(befejezetlenKocsmazas).getId();
        } else {
            throw new KocsmazasException(NINCS_VENDEG + vendegId);
        }
    }

    public void addToDetox(long vendegId) throws KocsmazasException {
        Kocsmazas kocsmazas = getBefejezetlenKocsmazasByVendegId(vendegId);
        if (kocsmazas != null) {
            kocsmazas.setDetoxbaKerult(true);
            try {
                kocsmazasRepository.save(kocsmazas);
            } catch (Exception e) {
                throw new KocsmazasException(SIKERTELEN);
            }
            finishKocsmazas(vendegId);
        } else {
            throw new KocsmazasException(NINCS_VENDEG + vendegId);
        }
    }

    public boolean vendegIsDetoxos(Long vendegId) throws KocsmazasException {
        int allKocsmazasByVendegDetoxban;
        try {
            allKocsmazasByVendegDetoxban = kocsmazasRepository.getKocsmazasCountByVendegDetoxban(vendegId);
        } catch (Exception e) {
            throw new KocsmazasException(NINCS_VENDEG + vendegId);
        }
        if (allKocsmazasByVendegDetoxban > MAX_DETOX) {
            return true;
        }
        return false;
    }

    public double getHetiAtlagosKocsmazasSzama(Long vendegId) throws KocsmazasException {
        int napokSzama = 0;
        int kocsmazasokSzama;

        try {
            kocsmazasokSzama = kocsmazasRepository.findAllByVendeg_Id(vendegId).size();
        } catch (Exception e) {
            throw new KocsmazasException(SIKERTELEN);
        }

        KocsmazasIdotartam kocsmazasIdotartam;
        try {
            kocsmazasIdotartam = kocsmazasRepository.findElsoEsUtolsoKocsmazasByVendegId(vendegId);
        } catch (Exception e) {
            throw new KocsmazasException(NINCS_KOCSMAZAS + vendegId);
        }

        if (kocsmazasIdotartam.getMettol() != null && kocsmazasIdotartam.getMeddig() != null) {
            napokSzama = (int) Duration.between(kocsmazasIdotartam.getMettol(), kocsmazasIdotartam.getMeddig()).toDays() + 1;
            int hetekSzama = (int) Math.ceil(napokSzama / 7.0);
            return (double) kocsmazasokSzama / hetekSzama;
        }
        return 0.0;
    }

    public double getVendegAtlagosFogyasztasAdatok(Long vendegId) throws KocsmazasException {
        int fogyasztasSuly = 0;
        List<FogyasztasAdatok> fogyasztasAdatok;
        try {
            fogyasztasAdatok = fogyasztasRepository.getFogyasztasAdatok(vendegId);
        } catch (Exception e) {
            throw new KocsmazasException(NINCS_FOGYASZTAS);
        }
        for (FogyasztasAdatok fa : fogyasztasAdatok) {
            fogyasztasSuly += (fa.getAdagMennyisege() * fa.getElfogyasztottMennyiseg() * fa.getAlkoholTartalom());
        }
        return (double) fogyasztasSuly / fogyasztasAdatok.size();
    }

    public boolean isAlkoholista(Long vendegId) throws KocsmazasException {
        return vendegIsDetoxos(vendegId) && getHetiAtlagosKocsmazasSzama(vendegId) > MAX_KOCSMAZAS && getVendegAtlagosFogyasztasAdatok(vendegId) > MAX_FOGYASZTAS;
    }

    public List<KocsmazasDto> isAlkoholistaWithCriteriaBuilder(Long vendegId) throws KocsmazasException {
        try {
            return kocsmazasConverter.convertKocsmazasListToKocsmazasDtoList(
                    kocsmazasRepository.isAlkoholistaWithCriteriaBuilder(vendegId));
        } catch (Exception e) {
            throw new KocsmazasException(NINCS_VENDEG);
        }
    }
}
