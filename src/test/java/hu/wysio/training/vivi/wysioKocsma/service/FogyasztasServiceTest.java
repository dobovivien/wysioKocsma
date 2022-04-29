package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.converter.FogyasztasConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysioKocsma.exception.FogyasztasException;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.repository.FogyasztasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FogyasztasServiceTest {

    Vendeg vendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);
    Ital ital = new Ital("italNev", 10, 10);
    Kocsmazas kocsmazas = new Kocsmazas(vendeg, LocalDateTime.now(), LocalDateTime.now(), null, false);
    Fogyasztas expectedFogyasztas = new Fogyasztas(ital, 10, kocsmazas);
    FogyasztasDto fogyasztasDto = new FogyasztasDto(1L, 2L, 10);

    @MockBean
    private FogyasztasConverter fogyasztasConverter;

    @MockBean
    private FogyasztasRepository fogyasztasRepository;

    @Autowired
    private FogyasztasService fogyasztasService;

    @Test
    void createFogyasztas_returns_fogyasztas() throws FogyasztasException {
        when(fogyasztasConverter.convertDtoToFogyasztas(fogyasztasDto)).thenReturn(expectedFogyasztas);
        when(fogyasztasRepository.save(expectedFogyasztas)).thenReturn(expectedFogyasztas);

        fogyasztasService.createFogyasztas(fogyasztasDto);

        verify(fogyasztasConverter).convertDtoToFogyasztas(fogyasztasDto);
        verify(fogyasztasRepository).save(expectedFogyasztas);
    }

    @Test
    void createFogyasztas_throws_siekrtelen_exception() {
        when(fogyasztasRepository.save(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.createFogyasztas(fogyasztasDto));
    }

    @Test
    void updateFogyasztas_finds_id() throws FogyasztasException {
        when(fogyasztasRepository.findById(1L)).thenReturn(Optional.of(expectedFogyasztas));

        fogyasztasService.updateFogyasztas(1L, expectedFogyasztas);

        verify(fogyasztasRepository).findById(1L);
    }

    @Test
    void updateFogyasztas_throws_nincsFogyasztas_exception() {
        when(fogyasztasRepository.findById(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.updateFogyasztas(1L, expectedFogyasztas));
    }

    @Test
    void updateFogyasztas_saves_fogyasztas() throws FogyasztasException {
        when(fogyasztasRepository.findById(1L)).thenReturn(Optional.of(expectedFogyasztas));
        when(fogyasztasRepository.save(expectedFogyasztas)).thenReturn(expectedFogyasztas);

        fogyasztasService.updateFogyasztas(1L, expectedFogyasztas);

        verify(fogyasztasRepository).save(expectedFogyasztas);
    }

    @Test
    void updateFogyasztas_throws_sikertelen_exception() {
        when(fogyasztasRepository.save(any())).thenThrow(new IllegalArgumentException());
        when(fogyasztasRepository.findById(1L)).thenReturn(Optional.of(expectedFogyasztas));

        Assertions.assertThrows(FogyasztasException.class, () -> fogyasztasService.updateFogyasztas(1L, expectedFogyasztas));
    }

    @Test
    void getLegtobbetFogyasztottItal_returns_ItalRongsorDto() {
        ItalRangsorDto italRangsorDto1 = new ItalRangsorDto("italNeve1", 10);
        ItalRangsorDto italRangsorDto2 = new ItalRangsorDto("italNeve2", 10);
        ItalRangsorDto italRangsorDto3 = new ItalRangsorDto("italNeve3", 10);

        List<ItalRangsorDto> italRangsorDtoList = new ArrayList<>();
        italRangsorDtoList.add(italRangsorDto1);
        italRangsorDtoList.add(italRangsorDto2);
        italRangsorDtoList.add(italRangsorDto3);

        when(fogyasztasRepository.getLegtobbetFogyasztottItal()).thenReturn(italRangsorDtoList);

        fogyasztasService.getLegtobbetFogyasztottItal();

        verify(fogyasztasRepository).getLegtobbetFogyasztottItal();
    }
}
