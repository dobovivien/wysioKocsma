package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.converter.ItalConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ItalException;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import hu.wysio.training.vivi.wysioKocsma.repository.ItalRepository;
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
            Ital ital = italRepository.save(italConverter.convertDtoToItal(italDto));
            return ital.getId();
        } catch (Exception e) {
            throw new ItalException(SIKERTELEN);
        }
    }

    public Ital updateItal(long id, Ital italAdat) throws ItalException {
        Ital ital;
        try {
            ital = italRepository.findById(id).get();
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
