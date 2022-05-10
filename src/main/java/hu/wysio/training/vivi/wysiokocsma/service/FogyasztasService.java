package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.exception.FogyasztasException;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FogyasztasService {

    private static final String NINCS_FOGYASZTAS = "Nincs ilyen fogyasztás az alabbi id-val: ";
    private static final String SIKERTELEN = "Sikertelen művelet.";

    @Autowired
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private FogyasztasConverter fogyasztasConverter;

    public Fogyasztas createFogyasztas(FogyasztasDto fogyasztasDto) throws FogyasztasException {
        try {
            return fogyasztasRepository.save(fogyasztasConverter.toFogyasztas(fogyasztasDto));

        } catch (IllegalArgumentException e) {
            throw new FogyasztasException(SIKERTELEN);
        }
    }

    public Fogyasztas updateFogyasztas(long id, Fogyasztas fogyasztasAdat) throws FogyasztasException {
        Fogyasztas fogyasztas;

        if (fogyasztasRepository.findById(id).isPresent()) {
            try {
                fogyasztas = fogyasztasRepository.getById(id);

            } catch (IllegalArgumentException e) {
                throw new FogyasztasException(NINCS_FOGYASZTAS + id);
            }

            try {
                fogyasztas.setItal(fogyasztasAdat.getItal());
                fogyasztas.setElfogyasztottMennyiseg(fogyasztasAdat.getElfogyasztottMennyiseg());

                return fogyasztasRepository.save(fogyasztas);

            } catch (IllegalArgumentException e) {
                throw new FogyasztasException(SIKERTELEN);
            }
        }
        throw new FogyasztasException(NINCS_FOGYASZTAS + id);
    }

    public List<ItalRangsorDto> getLegtobbetFogyasztottItal() {
        return fogyasztasRepository.getLegtobbetFogyasztottItal();
    }
}
