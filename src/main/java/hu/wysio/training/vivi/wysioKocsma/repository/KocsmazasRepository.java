package hu.wysio.training.vivi.wysioKocsma.repository;

import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.KocsmazasIdotartam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface KocsmazasRepository extends JpaRepository<Kocsmazas, Long> {

    List<Kocsmazas> findAllByVendeg_Id(Long vendegId);

    @Query("select count (k) from Kocsmazas k where k.vendeg.id = :vendegId and k.detoxbaKerult = true ")
    int getKocsmazasCountByVendegDetoxban(Long vendegId);

    @Query("select new hu.wysio.training.vivi.wysioKocsma.model.KocsmazasIdotartam(" +
            "min(k.mettol), max(k.mettol)) from Kocsmazas k where k.vendeg.id = :vendegId")
    KocsmazasIdotartam findElsoEsUtolsoKocsmazasByVendegId(Long vendegId);

    @Query("select max(k.mettol) from Kocsmazas k where k.vendeg.id = :vendegId")
    LocalDateTime findUtolsoKocsmazas(Long vendegId);

}