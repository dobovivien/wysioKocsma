package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.comparator.TabellaDtoComparator;
import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysiokocsma.exception.BunyoException;
import hu.wysio.training.vivi.wysiokocsma.model.Bunyo;
import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.BunyoRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BunyoService {

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
            throw new BunyoException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public List<TabellaDto> getTabellaEredmeny() throws BunyoException {
        try {
            List<Vendeg> vendegList = vendegRepository.findAll();

            Set<TabellaDto> tabellaDtoSet = vendegList.stream()
                    .map(vendeg -> {
                        long gyozelmekSzamaByVendeg = getGyozelmekSzama(vendeg.getId());
                        return vendegConverter.toTabellaDto(vendeg, gyozelmekSzamaByVendeg);
                    }).collect(Collectors.toSet());

            TabellaDtoComparator tabellaDtoComparator = new TabellaDtoComparator();

            return tabellaDtoSet.stream()
                    .sorted(tabellaDtoComparator)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BunyoException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    private long getGyozelmekSzama(Long vendegId) {
        return bunyoRepository.findAll().stream()
                .filter(bunyo -> isBunyoNyertese(bunyo, vendegId))
                .count();
    }

    private boolean isBunyoNyertese(Bunyo bunyo, Long vendegId) {
        return bunyo.getNyertes() != null && vendegId != null && vendegId.equals(bunyo.getNyertes().getId());
    }
}
