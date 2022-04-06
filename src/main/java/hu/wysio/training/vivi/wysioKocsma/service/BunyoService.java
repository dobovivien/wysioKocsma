package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.comparator.TabellaDtoComparator;
import hu.wysio.training.vivi.wysioKocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.repository.BunyoRepository;
import hu.wysio.training.vivi.wysioKocsma.repository.VendegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


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

    public long startBunyo() {
        List<Vendeg> vendegList = new ArrayList<>();
        Bunyo bunyo = new Bunyo();
        bunyo.setMettol(LocalDateTime.now());
        List<Kocsmazas> befejezetlenKocsmazasokList = kocsmazasService.getAllBefejezetlenKocsmazas().stream().toList();
        for (Kocsmazas kocsmazas : befejezetlenKocsmazasokList) {
            vendegList.add(kocsmazas.getVendeg());
        }
        bunyo.setVendegList(vendegList);
        return bunyoRepository.save(bunyo).getId();
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

    public static List<Long> removeDuplicates(List<Long> idList) {
        Set<Long> set = new LinkedHashSet<>();
        set.addAll(idList);
        idList.clear();
        idList.addAll(set);
        return idList;
    }

    public List<TabellaDto> getTabellaEredmeny() {
        List<TabellaDto> tabellaDtoList = new ArrayList<>();
        List<Vendeg> vendegList = vendegRepository.findAll();
        for (Vendeg vendeg : vendegList) {
            long bunyokSzama = getBunyokSzama(vendeg.getId());
            tabellaDtoList.add(vendegConverter.convertVendegToTabellaDto(vendeg, bunyokSzama));
        }
        TabellaDtoComparator tabellaDtoComparator = new TabellaDtoComparator();
        tabellaDtoList.sort(tabellaDtoComparator);
        return tabellaDtoList;
    }

    public long getBunyokSzama(Long vendegId) {
        List<Bunyo> bunyoList = bunyoRepository.findAll();
        return bunyoList.stream().map(bunyo -> bunyo.getVendegList().stream().filter(vendeg -> vendeg.getId() == vendegId)).count();
    }

//    public int getGyozelmekSzama(Long vendegId) {
//        int gyozelmekSzama = 0;
//        List<Vendeg> nyertesek = new ArrayList<>();
//        List<Bunyo> bunyoList = bunyoRepository.findAll();
//        for (Bunyo bunyo : bunyoList) {
//            nyertesek.add(bunyo.getNyertes());
//        }
//        for (Vendeg nyertes : nyertesek) {
//            if (nyertes.getId() == vendegId) {
//                gyozelmekSzama++;
//            }
//        }
//        return gyozelmekSzama;
//    } todo: mivel még nincsenek nyertesek, ezért NPE-vel elszáll


}
