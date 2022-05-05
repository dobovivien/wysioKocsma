package hu.wysio.training.vivi.wysiokocsma.repository;

import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.FogyasztasAdatok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FogyasztasRepository extends JpaRepository<Fogyasztas, Long>, FogyasztasRepositoryCustom {

    @Query("select new hu.wysio.training.vivi.wysiokocsma.model.FogyasztasAdatok(f.elfogyasztottMennyiseg, " +
            "(select i.alkoholTartalom from Ital i where i.id = f.ital.id), " +
            "(select i2.adagMennyisege  from Ital i2 where i2.id = f.ital.id)) " +
            "from Fogyasztas f, Kocsmazas k where f.kocsmazas.id = k.id and k.vendeg.id = :vendegId")
    List<FogyasztasAdatok> getFogyasztasAdatok(Long vendegId);

}
