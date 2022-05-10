package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.comparator.TabellaDtoComparator;
import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysiokocsma.exception.BunyoException;
import hu.wysio.training.vivi.wysiokocsma.model.Bunyo;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.BunyoRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BunyoService {

    private static final String SIKERTELEN = "Sikertelen művelet.";

    @Autowired
    private BunyoRepository bunyoRepository;

    @Autowired
    private KocsmazasService kocsmazasService;

    @Autowired
    private VendegRepository vendegRepository;

    @Autowired
    private VendegConverter vendegConverter;

    public long startBunyo() throws BunyoException {
        try {
            Set<Vendeg> vendegSet = new HashSet<>();

            Bunyo bunyo = new Bunyo();
            bunyo.setMettol(LocalDateTime.now());

            List<Kocsmazas> befejezetlenKocsmazasokList = kocsmazasService.getAllBefejezetlenKocsmazas();
            for (Kocsmazas kocsmazas : befejezetlenKocsmazasokList) {
                vendegSet.add(kocsmazas.getVendeg());
            }

            bunyo.setVendegList(vendegSet);

            return bunyoRepository.save(bunyo).getId();

        } catch (Exception e) {
            throw new BunyoException(SIKERTELEN);
        }
    }

    public List<TabellaDto> getTabellaEredmeny() throws BunyoException {
        try {
            Set<TabellaDto> tabellaDtoSet;

            List<Vendeg> vendegList = vendegRepository.findAll();

            tabellaDtoSet = vendegList.stream()
                    .map(vendeg -> {
                        try {
                            return vendegConverter.toTabellaDto(vendeg, vendeg.getBunyoList().size(),
                                    (int) getGyozelmekSzama(vendeg.getId()));
                        } catch (BunyoException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .collect(Collectors.toSet());

            TabellaDtoComparator tabellaDtoComparator = new TabellaDtoComparator();
            List<TabellaDto> tabellaDtoList = new ArrayList<>(tabellaDtoSet);

            return tabellaDtoList.stream()
                    .sorted(tabellaDtoComparator)
                    .collect(Collectors
                            .toList());

        } catch (Exception e) {
            throw new BunyoException(SIKERTELEN);
        }
    }

    public long getGyozelmekSzama(Long vendegId) throws BunyoException {
        try {
            List<Bunyo> bunyoList = bunyoRepository.findAll();

            return bunyoList.stream()
                    .filter(bunyo -> bunyo.getNyertes() != null && vendegId != null && vendegId.equals(bunyo.getNyertes().getId()))
                    .count();

        } catch (Exception e) {
            throw new BunyoException(SIKERTELEN);
        }
    }
}
