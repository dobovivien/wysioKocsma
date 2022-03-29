package hu.wysio.training.vivi.wysioKocsma.repository;

import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BunyoRepository extends JpaRepository<Bunyo, Long> {

//    public List<Bunyo> getAllByNyertesBeforeAndMeddig(Vendeg nyertes);
}
