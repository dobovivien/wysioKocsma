package hu.wysio.training.vivi.wysioKocsma.repository;

import hu.wysio.training.vivi.wysioKocsma.configuration.HibernateUtil;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class KocsmazasRepositoryCustomImpl implements KocsmazasRepositoryCustom {

    @Override
    public List<Kocsmazas> isAlkoholistaWithCriteriaBuilder(Long vendegId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Kocsmazas> criteriaQuery = criteriaBuilder.createQuery(Kocsmazas.class);
        Root<Kocsmazas> root = criteriaQuery.from(Kocsmazas.class);
        Join<Kocsmazas, Vendeg> joinVendeg = root.join(hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas_.vendeg);
        criteriaQuery.select(root).where(criteriaBuilder.equal(joinVendeg.get(hu.wysio.training.vivi.wysioKocsma.model.Vendeg_.id), vendegId));

        Query<Kocsmazas> query = session.createQuery(criteriaQuery);
        session.close();
        return query.getResultList();
    }
}
