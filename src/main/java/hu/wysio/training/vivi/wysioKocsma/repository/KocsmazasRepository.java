package hu.wysio.training.vivi.wysioKocsma.repository;

import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface KocsmazasRepository extends JpaRepository<Kocsmazas, Long> {

    List<Kocsmazas> findAllByVendeg_Id(Long vendegId);

    @Query("select count (k) from Kocsmazas k where k.vendeg.id = :vendegId and k.detoxbaKerult = true ")
    int findAllKocsmazasByVendegDetoxban(Long vendegId);

    @Query("select count(k) from  Kocsmazas k where k.vendeg.id = :vendegId and k.mettol between :elsoKocsmazas and :utolsoKocsmazas")
    int findallKocsmazasByHetiAlkalmakSzama(Long vendegId, LocalDateTime elsoKocsmazas, LocalDateTime utolsoKocsmazas);

    @Query("select min(k.mettol) from Kocsmazas k where k.vendeg.id = :vendegId")
    LocalDateTime findElsoKocsmazas(Long vendegId);

    @Query("select max(k.mettol) from Kocsmazas k where k.vendeg.id = :vendegId")
    LocalDateTime findUtolsoKocsmazas(Long vendegId);

}
