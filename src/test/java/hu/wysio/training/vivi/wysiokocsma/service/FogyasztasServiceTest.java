package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.exception.FogyasztasException;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hu.wysio.training.vivi.wysiokocsma.model.Majerosseg.BABAMAJ;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class FogyasztasServiceTest {

    public static final long ID = 1L;

    private static final Vendeg VENDEG = new Vendeg("TesztNev", BABAMAJ, 10, null, null);
    private static final Ital ITAL = new Ital("italNev", 10, 10);
    private static final Kocsmazas KOCSMAZAS = new Kocsmazas(VENDEG, LocalDateTime.now(), LocalDateTime.now(), null, false);
    private static final Fogyasztas EXPECTED_FOGYASZTAS = new Fogyasztas(ITAL, 10, KOCSMAZAS);
    private static final FogyasztasDto FOGYASZTAS_DTO = new FogyasztasDto(ID, 2L, 10);

    @MockBean
    private FogyasztasConverter fogyasztasConverter;

    @MockBean
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private FogyasztasService fogyasztasService;

    @Test
    void createFogyasztas_returns_fogyasztas() throws FogyasztasException {
        when(fogyasztasConverter.toEntity(FOGYASZTAS_DTO)).thenReturn(EXPECTED_FOGYASZTAS);
        when(fogyasztasRepository.save(EXPECTED_FOGYASZTAS)).thenReturn(EXPECTED_FOGYASZTAS);

        fogyasztasService.createFogyasztas(FOGYASZTAS_DTO);

        verify(fogyasztasConverter).toEntity(FOGYASZTAS_DTO);
        verify(fogyasztasRepository).save(EXPECTED_FOGYASZTAS);
    }

    @Test
    void createFogyasztas_throws_siekrtelen_exception() {
        when(fogyasztasRepository.save(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.createFogyasztas(FOGYASZTAS_DTO));

        verify(fogyasztasRepository).save(any());
    }

    @Test
    void updateFogyasztas_throws_nincsFogyasztas_exception() {
        when(fogyasztasRepository.getById(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.updateFogyasztas(ID, FOGYASZTAS_DTO));

        verify(fogyasztasRepository).getById(any());
    }

    @Test
    void updateFogyasztas_saves_fogyasztas() throws FogyasztasException {
        when(fogyasztasConverter.toEntity(FOGYASZTAS_DTO)).thenReturn(EXPECTED_FOGYASZTAS);
        when(fogyasztasRepository.getById(ID)).thenReturn(EXPECTED_FOGYASZTAS);
        when(fogyasztasRepository.save(EXPECTED_FOGYASZTAS)).thenReturn(EXPECTED_FOGYASZTAS);

        fogyasztasService.updateFogyasztas(ID, FOGYASZTAS_DTO);

        verify(fogyasztasConverter).toEntity(FOGYASZTAS_DTO);
        verify(fogyasztasRepository).getById(ID);
        verify(fogyasztasRepository).save(EXPECTED_FOGYASZTAS);
    }

    @Test
    void updateFogyasztas_throws_sikertelen_exception() {
        when(fogyasztasConverter.toEntity(FOGYASZTAS_DTO)).thenReturn(EXPECTED_FOGYASZTAS);
        when(fogyasztasRepository.getById(ID)).thenReturn(EXPECTED_FOGYASZTAS);
        when(fogyasztasRepository.save(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.updateFogyasztas(ID, FOGYASZTAS_DTO));

        verify(fogyasztasConverter).toEntity(FOGYASZTAS_DTO);
        verify(fogyasztasRepository).getById(ID);
        verify(fogyasztasRepository).save(any());
    }

    @Test
    void getLegtobbetFogyasztottItal_returns_ItalRongsorDto() {
        ItalRangsorDto italRangsorDto1 = new ItalRangsorDto("italNeve1", 10L);
        ItalRangsorDto italRangsorDto2 = new ItalRangsorDto("italNeve2", 10L);
        ItalRangsorDto italRangsorDto3 = new ItalRangsorDto("italNeve3", 10L);

        List<ItalRangsorDto> italRangsorDtoList = new ArrayList<>();
        italRangsorDtoList.add(italRangsorDto1);
        italRangsorDtoList.add(italRangsorDto2);
        italRangsorDtoList.add(italRangsorDto3);

        when(fogyasztasRepository.getLegtobbetFogyasztottItal()).thenReturn(italRangsorDtoList);

        fogyasztasService.getLegtobbetFogyasztottItal();

        verify(fogyasztasRepository).getLegtobbetFogyasztottItal();
    }
}
