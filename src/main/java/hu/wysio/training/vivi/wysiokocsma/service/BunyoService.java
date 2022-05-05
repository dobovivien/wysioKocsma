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

//    public Bunyo finishBunyo(long id, Bunyo bunyoAdat) throws ResourceNotFoundException {
//        Bunyo bunyo = bunyoRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen bunyo az alabbi id-val: " + id));
//
//        bunyo.setMettol(bunyoAdat.getMettol());
//        bunyo.setMeddig(bunyoAdat.getMeddig());
//        bunyo.setVendegList(bunyoAdat.getVendegList());
//        bunyo.setNyertes(bunyoAdat.getNyertes());
//
//        return bunyoRepository.save(bunyo);
//    } todo: később kelleni fog

    public List<TabellaDto> getTabellaEredmeny() throws BunyoException {
        try {
            Set<TabellaDto> tabellaDtoSet = new HashSet<>();
            List<Vendeg> vendegList = vendegRepository.findAll();
            for (Vendeg vendeg : vendegList) {
                int bunyokSzama = vendeg.getBunyoList().size();
                tabellaDtoSet.add(vendegConverter.convertVendegToTabellaDto(vendeg, bunyokSzama, getGyozelmekSzama(vendeg.getId())));
            }
            TabellaDtoComparator tabellaDtoComparator = new TabellaDtoComparator();
            List<TabellaDto> tabellaDtoList = new ArrayList<>(tabellaDtoSet);
            return tabellaDtoList.stream().sorted(tabellaDtoComparator).collect(Collectors.toList());
        } catch (Exception e) {
            throw new BunyoException(SIKERTELEN);
        }
    }

    public int getGyozelmekSzama(Long vendegId) throws BunyoException {
        int gyozelmekSzama = 0;
        try {
            List<Bunyo> bunyoList = bunyoRepository.findAll();
            for (Bunyo bunyo : bunyoList) {
                if (bunyo.getNyertes() != null && vendegId != null && vendegId.equals(bunyo.getNyertes().getId())) {
                    gyozelmekSzama++;
                }
            }
            return gyozelmekSzama;
        } catch (Exception e) {
            throw new BunyoException(SIKERTELEN);
        }
    }
}
