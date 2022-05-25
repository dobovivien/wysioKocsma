package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.ItalConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.repository.ItalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItalService {

    @Autowired
    private ItalRepository italRepository;

    @Autowired
    private ItalConverter italConverter;

    public long createItal(ItalDto italDto) {
        Ital ital = italRepository.save(italConverter.toEntity(italDto));

        return ital.getId();
    }

    public Ital updateItal(Long id, ItalDto italDto) {
        Ital updatedItal = italConverter.toEntity(italDto);

        Ital ital = italRepository.getById(id);

        ital.setNev(updatedItal.getNev());
        ital.setAdagMennyisege(updatedItal.getAdagMennyisege());
        ital.setAlkoholTartalom(updatedItal.getAlkoholTartalom());

        return italRepository.save(ital);
    }

    public List<Ital> findAll() {
        return italRepository.findAll();
    }

    public void deleteItal(Long italId) {
        italRepository.deleteById(italId);
    }
}
