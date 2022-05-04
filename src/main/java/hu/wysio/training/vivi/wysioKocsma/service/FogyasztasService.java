package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysioKocsma.exception.FogyasztasExceptionWysio;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.repository.FogyasztasRepository;
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

    public Fogyasztas createFogyasztas(FogyasztasDto fogyasztasDto) throws FogyasztasExceptionWysio {
        try {
            return fogyasztasRepository.save(fogyasztasConverter.convertDtoToFogyasztas(fogyasztasDto));
        } catch (Exception e) {
            throw new FogyasztasExceptionWysio(SIKERTELEN);
        }
    }

    public Fogyasztas updateFogyasztas(long id, Fogyasztas fogyasztasAdat) throws FogyasztasExceptionWysio {
        Fogyasztas fogyasztas;
        try {
            fogyasztas = fogyasztasRepository.findById(id).get();
        } catch (Exception e) {
            throw new FogyasztasExceptionWysio(NINCS_FOGYASZTAS + id);
        }
        try {
            fogyasztas.setItal(fogyasztasAdat.getItal());
            fogyasztas.setElfogyasztottMennyiseg(fogyasztasAdat.getElfogyasztottMennyiseg());
            return fogyasztasRepository.save(fogyasztas);
        } catch (Exception e) {
            throw new FogyasztasExceptionWysio(SIKERTELEN);
        }
    }

    public List<ItalRangsorDto> getLegtobbetFogyasztottItal() {
        return fogyasztasRepository.getLegtobbetFogyasztottItal();
    }
}
