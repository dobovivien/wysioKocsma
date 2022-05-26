package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.exception.FogyasztasException;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FogyasztasService {

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private FogyasztasConverter fogyasztasConverter;

    public Fogyasztas createFogyasztas(FogyasztasDto fogyasztasDto) {
        return fogyasztasRepository.save(fogyasztasConverter.toEntity(fogyasztasDto));
    }

    public Fogyasztas updateFogyasztas(Long id, FogyasztasDto fogyasztasDto) throws WysioKocsmaException {
        Optional<Fogyasztas> fogyasztas = fogyasztasRepository.findById(id);

        if (fogyasztas.isEmpty()) {
            throw new FogyasztasException(ExceptionMessage.NINCS_FOGYASZTAS);
        }

        Fogyasztas updatedFogyasztas = fogyasztasConverter.toEntity(fogyasztasDto);

        fogyasztas.get().setKocsmazas(updatedFogyasztas.getKocsmazas());
        fogyasztas.get().setItal(updatedFogyasztas.getItal());
        fogyasztas.get().setElfogyasztottMennyiseg(updatedFogyasztas.getElfogyasztottMennyiseg());

        return fogyasztasRepository.save(fogyasztas.get());
    }

    public List<ItalRangsorDto> getLegtobbetFogyasztottItal() {
        return fogyasztasRepository.getLegtobbetFogyasztottItal();
    }
}
