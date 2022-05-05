package hu.wysio.training.vivi.wysiokocsma.repository;

import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;

import java.util.List;

public interface FogyasztasRepositoryCustom {

    List<ItalRangsorDto> getLegtobbetFogyasztottItal();
}
