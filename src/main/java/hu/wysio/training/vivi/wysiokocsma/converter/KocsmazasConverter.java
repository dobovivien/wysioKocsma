package hu.wysio.training.vivi.wysiokocsma.converter;

import hu.wysio.training.vivi.wysiokocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysiokocsma.model.AbstractEntity;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KocsmazasConverter {

    public KocsmazasDto toDto(Kocsmazas kocsmazas) {
        KocsmazasDto kocsmazasDto = new KocsmazasDto();

        kocsmazasDto.setMettol(kocsmazas.getMettol());
        kocsmazasDto.setMeddig(kocsmazas.getMeddig());
        kocsmazasDto.setDetoxbaKerult(kocsmazas.isDetoxbaKerult());

        List<Long> fogyasztasDtoList = kocsmazas.getFogyasztasLista().stream()
                .map(AbstractEntity::getId)
                .collect(Collectors.toList());

        kocsmazasDto.setFogyasztasLista(fogyasztasDtoList);

        return kocsmazasDto;
    }

    public List<KocsmazasDto> toDtoList(List<Kocsmazas> kocsmazasList) {
        return kocsmazasList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
