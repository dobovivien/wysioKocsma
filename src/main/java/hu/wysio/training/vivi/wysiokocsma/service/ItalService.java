package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.ItalConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.exception.ItalException;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.repository.ItalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItalService {

    private static final String NINCS_ITAL = "Nincs ilyen ital az alabbi id-val: ";
    private static final String SIKERTELEN = "Sikertelen művelet.";

    @Autowired
    private ItalRepository italRepository;

    @Autowired
    private ItalConverter italConverter;

    public long createItal(ItalDto italDto) throws ItalException {
        try {
            Ital ital = italRepository.save(italConverter.toItal(italDto));

            return ital.getId();

        } catch (Exception e) {
            throw new ItalException(SIKERTELEN);
        }
    }

    public Ital updateItal(long id, Ital italAdat) throws ItalException {
        Ital ital;

        if (italRepository.findById(id).isPresent()) {
            try {
                ital = italRepository.getById(id);

            } catch (Exception e) {
                throw new ItalException(NINCS_ITAL + id);
            }

            try {
                ital.setNev(italAdat.getNev());
                ital.setAdagMennyisege(italAdat.getAdagMennyisege());
                ital.setAlkoholTartalom(italAdat.getAlkoholTartalom());

                return italRepository.save(ital);

            } catch (Exception e) {
                throw new ItalException(SIKERTELEN);
            }
        }

        throw new ItalException(NINCS_ITAL + id);
    }

    public List<Ital> findAll() throws ItalException {
        try {
            return italRepository.findAll();

        } catch (Exception e) {
            throw new ItalException(SIKERTELEN);
        }
    }

    public void deleteItal(Ital italAdat) throws ItalException {
        try {
            italRepository.delete(italAdat);
            
        } catch (Exception e) {
            throw new ItalException(NINCS_ITAL + italAdat.getId());
        }
    }
}
