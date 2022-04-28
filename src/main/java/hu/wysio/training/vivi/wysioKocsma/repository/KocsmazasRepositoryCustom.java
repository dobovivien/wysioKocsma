package hu.wysio.training.vivi.wysioKocsma.repository;

import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;

import java.util.List;

public interface KocsmazasRepositoryCustom {

    List<Kocsmazas> isAlkoholistaWithCriteriaBuilder(Long vendegId);
}
