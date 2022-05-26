package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.ItalConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.exception.ItalException;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.repository.ItalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public Ital updateItal(Long id, ItalDto italDto) throws WysioKocsmaException {
        Optional<Ital> ital = italRepository.findById(id);

        if (ital.isEmpty()) {
            throw new ItalException(ExceptionMessage.NINCS_ITAL);
        }
        Ital updatedItal = italConverter.toEntity(italDto);

        ital.get().setNev(updatedItal.getNev());
        ital.get().setAdagMennyisege(updatedItal.getAdagMennyisege());
        ital.get().setAlkoholTartalom(updatedItal.getAlkoholTartalom());

        return italRepository.save(ital.get());
    }

    public List<Ital> findAll() {
        return italRepository.findAll();
    }

    public void deleteItal(Long italId) throws WysioKocsmaException {
        if (italRepository.findById(italId).isEmpty()) {
            throw new ItalException(ExceptionMessage.NINCS_ITAL);
        }

        italRepository.deleteById(italId);
    }
}
