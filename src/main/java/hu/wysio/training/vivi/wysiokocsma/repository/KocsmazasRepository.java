package hu.wysio.training.vivi.wysiokocsma.repository;

import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.KocsmazasIdotartam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KocsmazasRepository extends JpaRepository<Kocsmazas, Long>, KocsmazasRepositoryCustom {

    List<Kocsmazas> findAllByVendegId(Long vendegId);

    @Query("select count (k) from Kocsmazas k where k.vendeg.id = :vendegId and k.detoxbaKerult = true ")
    int getKocsmazasCountByVendegDetoxban(Long vendegId);

    @Query("select new hu.wysio.training.vivi.wysiokocsma.model.KocsmazasIdotartam(" +
            "min(k.mettol), max(k.mettol)) from Kocsmazas k where k.vendeg.id = :vendegId")
    KocsmazasIdotartam findElsoEsUtolsoKocsmazasByVendegId(Long vendegId);
}