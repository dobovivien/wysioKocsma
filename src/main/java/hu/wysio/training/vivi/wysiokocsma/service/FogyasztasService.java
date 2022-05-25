package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
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

    public Fogyasztas createFogyasztas(FogyasztasDto fogyasztasDto) {
        return fogyasztasRepository.save(fogyasztasConverter.toEntity(fogyasztasDto));
    }

    public Fogyasztas updateFogyasztas(Long id, FogyasztasDto fogyasztasDto) {
        Fogyasztas updatedFogyasztas = fogyasztasConverter.toEntity(fogyasztasDto);

        Fogyasztas fogyasztas = fogyasztasRepository.getById(id);

        fogyasztas.setKocsmazas(updatedFogyasztas.getKocsmazas());
        fogyasztas.setItal(updatedFogyasztas.getItal());
        fogyasztas.setElfogyasztottMennyiseg(updatedFogyasztas.getElfogyasztottMennyiseg());

        return fogyasztasRepository.save(fogyasztas);
    }

    public List<ItalRangsorDto> getLegtobbetFogyasztottItal() {
        return fogyasztasRepository.getLegtobbetFogyasztottItal();
    }
}
