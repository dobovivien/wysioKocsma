package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.ItalConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.exception.ItalException;
import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
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

    public long createItal(ItalDto italDto) throws ItalException {
        try {
            Ital ital = italRepository.save(italConverter.toEntity(italDto));

            return ital.getId();

        } catch (Exception e) {
            throw new ItalException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public Ital updateItal(Long id, ItalDto italDto) throws ItalException {
        Ital ital;
        Ital updatedItal = italConverter.toEntity(italDto);

        try {
            ital = italRepository.getById(id);

        } catch (Exception e) {
            throw new ItalException(ExceptionMessage.NINCS_ITAL.getMessage() + id);
        }

        try {
            ital.setNev(updatedItal.getNev());
            ital.setAdagMennyisege(updatedItal.getAdagMennyisege());
            ital.setAlkoholTartalom(updatedItal.getAlkoholTartalom());

            return italRepository.save(ital);

        } catch (Exception e) {
            throw new ItalException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public List<Ital> findAll() throws ItalException {
        try {
            return italRepository.findAll();

        } catch (Exception e) {
            throw new ItalException(ExceptionMessage.SIKERTELEN.getMessage());
        }
    }

    public void deleteItal(Long italId) throws ItalException {
        try {
            italRepository.deleteById(italId);

        } catch (Exception e) {
            throw new ItalException(ExceptionMessage.NINCS_ITAL.getMessage() + italId);
        }
    }
}
