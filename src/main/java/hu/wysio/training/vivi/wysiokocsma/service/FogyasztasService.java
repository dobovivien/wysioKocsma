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
        Optional<Fogyasztas> fogyasztasOptional = fogyasztasRepository.findById(id);

        if (fogyasztasOptional.isEmpty()) {
            throw new FogyasztasException(ExceptionMessage.NINCS_FOGYASZTAS);
        }

        Fogyasztas updatedFogyasztas = fogyasztasConverter.toEntity(fogyasztasDto);

        Fogyasztas fogyasztas = fogyasztasOptional.get();

        fogyasztas.setKocsmazas(updatedFogyasztas.getKocsmazas());
        fogyasztas.setItal(updatedFogyasztas.getItal());
        fogyasztas.setElfogyasztottMennyiseg(updatedFogyasztas.getElfogyasztottMennyiseg());

        return fogyasztasRepository.save(fogyasztas);
    }

    public List<ItalRangsorDto> getLegtobbetFogyasztottItal() {
        return fogyasztasRepository.getLegtobbetFogyasztottItal();
    }
}
