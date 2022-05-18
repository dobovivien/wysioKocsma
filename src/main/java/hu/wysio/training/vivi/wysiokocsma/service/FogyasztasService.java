package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.exception.FogyasztasException;
import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FogyasztasService {

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private FogyasztasConverter fogyasztasConverter;

    public Fogyasztas createFogyasztas(FogyasztasDto fogyasztasDto) throws FogyasztasException {
        try {
            return fogyasztasRepository.save(fogyasztasConverter.toEntity(fogyasztasDto));

        } catch (IllegalArgumentException e) {
            throw new FogyasztasException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public Fogyasztas updateFogyasztas(long id, FogyasztasDto fogyasztasDto) throws FogyasztasException {
        Fogyasztas fogyasztas;
        Fogyasztas updatedFogyasztas = fogyasztasConverter.toEntity(fogyasztasDto);

        try {
            fogyasztas = fogyasztasRepository.getById(id);

        } catch (IllegalArgumentException e) {
            throw new FogyasztasException(ExceptionMessage.NINCS_FOGYASZTAS.getMessage() + id);
        }

        try {
            fogyasztas.setKocsmazas(updatedFogyasztas.getKocsmazas());
            fogyasztas.setItal(updatedFogyasztas.getItal());
            fogyasztas.setElfogyasztottMennyiseg(updatedFogyasztas.getElfogyasztottMennyiseg());

            return fogyasztasRepository.save(fogyasztas);

        } catch (IllegalArgumentException e) {
            throw new FogyasztasException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public List<ItalRangsorDto> getLegtobbetFogyasztottItal() {
        return fogyasztasRepository.getLegtobbetFogyasztottItal();
    }
}
