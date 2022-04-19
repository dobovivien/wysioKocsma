package hu.wysio.training.vivi.wysioKocsma.converter;

import hu.wysio.training.vivi.wysioKocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.repository.FogyasztasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KocsmazasConverter {

    @Autowired
    private FogyasztasConverter fogyasztasConverter;

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    public KocsmazasDto convertKocsmazasToDto(Kocsmazas kocsmazas) {
        List<Long> fogyasztasDtoList = new ArrayList<>();
        KocsmazasDto kocsmazasDto = new KocsmazasDto();
        kocsmazasDto.setMettol(kocsmazas.getMettol());
        kocsmazasDto.setMeddig(kocsmazas.getMeddig());
        kocsmazasDto.setDetoxbaKerult(kocsmazas.isDetoxbaKerult());
        for (Fogyasztas fogyasztas : kocsmazas.getFogyasztasLista()) {
            fogyasztasDtoList.add(fogyasztas.getId());
        }
        kocsmazasDto.setFogyasztasLista(fogyasztasDtoList);
        return kocsmazasDto;
    }

    public Kocsmazas convertDtoToKocsmazas(KocsmazasDto kocsmazasDto) {
        List<Fogyasztas> fogyasztasList = new ArrayList<>();
        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setMettol(kocsmazasDto.getMettol());
        kocsmazas.setMeddig(kocsmazasDto.getMeddig());
        kocsmazas.setDetoxbaKerult(kocsmazasDto.isDetoxbaKerult());
        for (Long id : kocsmazasDto.getFogyasztasLista()) {
            fogyasztasList.add(fogyasztasRepository.getById(id));
        }
        kocsmazas.setFogyasztasLista(fogyasztasList);
        return kocsmazas;
    }

    public List<KocsmazasDto> convertKocsmazasListToKocsmazasDtoList(List<Kocsmazas> kocsmazasList) {
        List<KocsmazasDto> kocsmazasDtoList = new ArrayList<>();
        for (Kocsmazas kocsmazas : kocsmazasList) {
            kocsmazasDtoList.add(convertKocsmazasToDto(kocsmazas));
        }
        return kocsmazasDtoList;
    }
}
