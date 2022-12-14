package hu.wysio.training.vivi.wysiokocsma.repository;

import hu.wysio.training.vivi.wysiokocsma.configuration.HibernateUtil;
import hu.wysio.training.vivi.wysiokocsma.converter.ItalConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas_;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.model.Ital_;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class FogyasztasRepositoryCustomImpl implements FogyasztasRepositoryCustom {

    private static final int LIMIT = 3;

    @Autowired
    private ItalConverter italConverter;

    @Override
    public List<ItalRangsorDto> getLegtobbetFogyasztottItal() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);

        Root<Fogyasztas> root = criteriaQuery.from(Fogyasztas.class);
        Join<Fogyasztas, Ital> italok = root.join(Fogyasztas_.ital, JoinType.LEFT);
        Expression<Long> countItalFajta = criteriaBuilder.count(root);
        criteriaQuery
                .select(criteriaBuilder.tuple(italok.get(Ital_.nev), countItalFajta))
                .groupBy(italok.get(Ital_.nev))
                .orderBy(criteriaBuilder.desc(countItalFajta));

        List<Tuple> query = session.createQuery(criteriaQuery).setMaxResults(LIMIT).getResultList();
        List<ItalRangsorDto> returnList = new ArrayList<>();

        query.forEach(tuple -> returnList.add(italConverter.toItalRangsorDto(tuple)));

        session.close();
        
        return returnList;
    }
}
