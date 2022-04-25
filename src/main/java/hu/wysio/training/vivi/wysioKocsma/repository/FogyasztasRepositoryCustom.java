package hu.wysio.training.vivi.wysioKocsma.repository;

import hu.wysio.training.vivi.wysioKocsma.dto.ItalRangsorDto;

import java.util.List;

public interface FogyasztasRepositoryCustom {

    List<ItalRangsorDto> getLegtobbetFogyasztottItal();
}
