package hu.wysio.training.vivi.wysiokocsma.repository;

import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;

import java.util.List;

public interface KocsmazasRepositoryCustom {

    List<Kocsmazas> isAlkoholistaWithCriteriaBuilder(Long vendegId);
}
