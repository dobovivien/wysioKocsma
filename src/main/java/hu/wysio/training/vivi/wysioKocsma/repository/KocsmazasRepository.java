package hu.wysio.training.vivi.wysioKocsma.repository;

import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KocsmazasRepository extends JpaRepository<Kocsmazas, Long> {

    List<Kocsmazas> findAllByVendeg(Vendeg vendeg);
}
