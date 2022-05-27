package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.exception.FogyasztasException;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.FogyasztasRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.ItalRepository;
import hu.wysio.training.vivi.wysiokocsma.repository.KocsmazasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final Fogyasztas FOGYASZTAS = new Fogyasztas(ITAL, 10, KOCSMAZAS);
    private static final FogyasztasDto FOGYASZTAS_DTO = new FogyasztasDto(ID, 2L, 10);

    @MockBean
    private FogyasztasConverter fogyasztasConverter;

    @MockBean
    private FogyasztasRepository fogyasztasRepository;

    @MockBean
    private KocsmazasRepository kocsmazasRepository;

    @MockBean
    private ItalRepository italRepository;

    @Autowired
    private FogyasztasService fogyasztasService;

    @Test
    void createFogyasztas_returns_fogyasztas() {
        when(fogyasztasConverter.toEntity(FOGYASZTAS_DTO)).thenReturn(FOGYASZTAS);
        when(fogyasztasRepository.save(FOGYASZTAS)).thenReturn(FOGYASZTAS);

        fogyasztasService.createFogyasztas(FOGYASZTAS_DTO);

        verify(fogyasztasConverter).toEntity(FOGYASZTAS_DTO);
        verify(fogyasztasRepository).save(FOGYASZTAS);
    }

    @Test
    void updateFogyasztas_saves_fogyasztas() throws WysioKocsmaException {

        when(fogyasztasRepository.findById(any())).thenReturn(Optional.of(FOGYASZTAS));
        when(kocsmazasRepository.findById(any())).thenReturn(Optional.of(KOCSMAZAS));
        when(italRepository.findById(any())).thenReturn(Optional.of(ITAL));
        when(fogyasztasRepository.save(FOGYASZTAS)).thenReturn(FOGYASZTAS);

        Fogyasztas resultFogyasztas = fogyasztasService.updateFogyasztas(ID, FOGYASZTAS_DTO);

        Assertions.assertEquals(FOGYASZTAS.getElfogyasztottMennyiseg(), resultFogyasztas.getElfogyasztottMennyiseg());

        verify(fogyasztasRepository).findById(any());
        verify(kocsmazasRepository).findById(any());
        verify(italRepository).findById(any());
        verify(fogyasztasRepository).save(FOGYASZTAS);
    }

    @Test
    void updateFogyasztas_throws_nincsFogyasztas_exception() {
        when(fogyasztasRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.updateFogyasztas(ID, FOGYASZTAS_DTO));

        verify(fogyasztasRepository).findById(any());
    }

    @Test
    void updateFogyasztas_throws_nincsKocsmazas_exception() {
        when(fogyasztasRepository.findById(any())).thenReturn(Optional.of(FOGYASZTAS));
        when(kocsmazasRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.updateFogyasztas(ID, FOGYASZTAS_DTO));

        verify(kocsmazasRepository).findById(any());
    }

    @Test
    void updateFogyasztas_throws_nincsItal_exception() {
        when(fogyasztasRepository.findById(any())).thenReturn(Optional.of(FOGYASZTAS));
        when(kocsmazasRepository.findById(any())).thenReturn(Optional.of(KOCSMAZAS));
        when(italRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.updateFogyasztas(ID, FOGYASZTAS_DTO));

        verify(italRepository).findById(any());
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
