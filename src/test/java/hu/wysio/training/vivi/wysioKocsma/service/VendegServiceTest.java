package hu.wysio.training.vivi.wysioKocsma.service;

import hu.wysio.training.vivi.wysioKocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysioKocsma.exception.VendegException;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.repository.VendegRepository;
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
class VendegServiceTest {

    @MockBean
    private VendegConverter vendegConverter;

    @MockBean
    private VendegRepository vendegRepository;

    @Autowired
    private VendegService vendegService;

    @Test
    void createVendeg_returns_vendegId() throws VendegException {
        Vendeg expectedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);
        VendegDto vendegDto = new VendegDto(1L, "TesztNev", "babamaj", 10, null, null);

        when(vendegConverter.convertDtoToVendeg(vendegDto)).thenReturn(expectedVendeg);
        when(vendegRepository.save(expectedVendeg)).thenReturn(expectedVendeg);

        vendegService.createVendeg(vendegDto);

        verify(vendegConverter).convertDtoToVendeg(vendegDto);
        verify(vendegRepository).save(expectedVendeg);
    }

    @Test
    void createVendeg_throws_siekrtelen_exception() {
        VendegDto vendegDto = new VendegDto(1L, "TesztNev", "babamaj", 10, null, null);

        when(vendegRepository.save(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.createVendeg(vendegDto));
    }

    @Test
    void updateVendeg_finds_id() throws VendegException {
        Vendeg expectedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);

        when(vendegRepository.findById(1L)).thenReturn(Optional.of(expectedVendeg));

        vendegService.updateVendeg(1L, expectedVendeg);

        verify(vendegRepository).findById(1L);
    }

    @Test
    void updateVendeg_throws_nincsVendeg_exception() {
        Vendeg expectedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);

        when(vendegRepository.findById(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.updateVendeg(1L, expectedVendeg));
    }

    @Test
    void updateVendeg_saves_vendeg() throws VendegException {
        Vendeg expectedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);

        when(vendegRepository.findById(1L)).thenReturn(Optional.of(expectedVendeg));
        when(vendegRepository.save(expectedVendeg)).thenReturn(expectedVendeg);

        vendegService.updateVendeg(1L, expectedVendeg);

        verify(vendegRepository).save(expectedVendeg);
    }

    @Test
    void updateVendeg_throws_sikertelen_exception() {
        Vendeg expectedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);

        when(vendegRepository.save(any())).thenThrow(new IllegalArgumentException());
        when(vendegRepository.findById(1L)).thenReturn(Optional.of(expectedVendeg));

        Assertions.assertThrows(VendegException.class, () -> vendegService.updateVendeg(1L, expectedVendeg));
    }

    @Test
    void findAll_returns_all_vendeg() throws VendegException {
        Vendeg expectedVendeg1 = new Vendeg("TesztNev1", "babamaj", 10, null, null);
        Vendeg expectedVendeg2 = new Vendeg("TesztNev2", "babamaj", 10, null, null);
        Vendeg expectedVendeg3 = new Vendeg("TesztNev3", "babamaj", 10, null, null);
        Vendeg expectedVendeg4 = new Vendeg("TesztNev4", "babamaj", 10, null, null);

        List<Vendeg> vendegList = new ArrayList<>();
        vendegList.add(expectedVendeg1);
        vendegList.add(expectedVendeg2);
        vendegList.add(expectedVendeg3);
        vendegList.add(expectedVendeg4);

        when(vendegRepository.findAll()).thenReturn(vendegList);

        vendegService.findAll();

        verify(vendegRepository).findAll();
    }

    @Test
    void findAll_throws_nincsVendeg_exception() {
        when(vendegRepository.findAll()).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.findAll());
    }

    @Test
    void findById_returns_vendeg_by_id() throws VendegException {
        Vendeg expectedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);

        when(vendegRepository.findById(1L)).thenReturn(Optional.of(expectedVendeg));

        vendegService.findById(1L);

        verify(vendegRepository).findById(1L);
    }

    @Test
    void findById_throws_nincsVendeg_exception() {
        when(vendegRepository.findById(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.findById(1L));
    }

    @Test
    void getVendegekByElfogyasztottMennyiseg_returns_all_vendeg() throws VendegException {
        Vendeg expectedVendeg1 = new Vendeg("TesztNev1", "babamaj", 10, null, null);
        Vendeg expectedVendeg2 = new Vendeg("TesztNev2", "babamaj", 10, null, null);
        Vendeg expectedVendeg3 = new Vendeg("TesztNev3", "babamaj", 10, null, null);
        Vendeg expectedVendeg4 = new Vendeg("TesztNev4", "babamaj", 10, null, null);

        List<Vendeg> vendegList = new ArrayList<>();
        vendegList.add(expectedVendeg1);
        vendegList.add(expectedVendeg2);
        vendegList.add(expectedVendeg3);
        vendegList.add(expectedVendeg4);

        long fogyasztasByVendegId = vendegService.getElfogyasztottMennyisegByVendegId(expectedVendeg1.getId());

        VendegFogyasztasSzerintDto vendegFogyasztasSzerintDto = new VendegFogyasztasSzerintDto("becenev", 1L);

        when(vendegRepository.findAll()).thenReturn(vendegList);
        when(vendegConverter.convertVendegToVFSZDto(expectedVendeg1, fogyasztasByVendegId)).thenReturn(vendegFogyasztasSzerintDto);

        vendegService.getVendegekByElfogyasztottMennyiseg();

        verify(vendegRepository).findAll();
        verify(vendegConverter).convertVendegToVFSZDto(expectedVendeg1, fogyasztasByVendegId);
    }

    @Test
    void getElfogyasztottMennyisegByVendegId_returns_vendeg() throws VendegException {
        Vendeg expectedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);

        Kocsmazas kocsmazas1 = new Kocsmazas(expectedVendeg, LocalDateTime.now(), LocalDateTime.now(), null, false);
        Kocsmazas kocsmazas2 = new Kocsmazas(expectedVendeg, LocalDateTime.now(), LocalDateTime.now(), null, false);

        List<Kocsmazas> kocsmazasList = new ArrayList<>();
        kocsmazasList.add(kocsmazas1);
        kocsmazasList.add(kocsmazas2);

        Ital ital = new Ital("italNev", 10, 10);

        Fogyasztas fogyasztas1 = new Fogyasztas(ital, 10, kocsmazas1);
        Fogyasztas fogyasztas2 = new Fogyasztas(ital, 10, kocsmazas2);

        List<Fogyasztas> fogyasztasList = new ArrayList<>();
        fogyasztasList.add(fogyasztas1);
        fogyasztasList.add(fogyasztas2);

        kocsmazas1.setFogyasztasLista(fogyasztasList);
        kocsmazas2.setFogyasztasLista(fogyasztasList);
        expectedVendeg.setKocsmazasList(kocsmazasList);

        when(vendegRepository.findById(1L)).thenReturn(Optional.of(expectedVendeg));

        vendegService.getElfogyasztottMennyisegByVendegId(1L);

        verify(vendegRepository).findById(1L);
    }

    @Test
    void getElfogyasztottMennyisegByVendegId_throws_nincsVendeg_exception() {
        when(vendegRepository.findById(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.getElfogyasztottMennyisegByVendegId(1L));
    }
}
