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
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class VendegServiceTest {

    private static final Vendeg EXPECTED_VENDEG = new Vendeg("TesztNev", "babamaj", 10, null, null);
    private static final VendegDto VENDEG_DTO = new VendegDto(1L, "TesztNev", "babamaj", 10, null, null);

    private static final Vendeg EXPECTED_VENDEG_1 = new Vendeg("TesztNev1", "babamaj", 10, null, null);
    private static final Vendeg EXPECTED_VENDEG_2 = new Vendeg("TesztNev2", "babamaj", 10, null, null);
    private static final Vendeg EXPECTED_VENDEG_3 = new Vendeg("TesztNev3", "babamaj", 10, null, null);
    private static final Vendeg EXPECTED_VENDEG_4 = new Vendeg("TesztNev4", "babamaj", 10, null, null);

    @MockBean
    private VendegConverter vendegConverter;

    @MockBean
    private VendegRepository vendegRepository;

    @Autowired
    private VendegService vendegService;

    @Test
    void createVendeg_returns_vendegId() throws VendegException {
        when(vendegConverter.convertDtoToVendeg(VENDEG_DTO)).thenReturn(EXPECTED_VENDEG);
        when(vendegRepository.save(EXPECTED_VENDEG)).thenReturn(EXPECTED_VENDEG);

        vendegService.createVendeg(VENDEG_DTO);

        verify(vendegConverter).convertDtoToVendeg(VENDEG_DTO);
        verify(vendegRepository).save(EXPECTED_VENDEG);
    }

    @Test
    void createVendeg_throws_siekrtelen_exception() {
        when(vendegRepository.save(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.createVendeg(VENDEG_DTO));
    }

    @Test
    void updateVendeg_finds_id() throws VendegException {
        Vendeg vendegToUpdate = new Vendeg("TesztNevdfgh", "babamdfghaj", 10, null, null);
        vendegToUpdate.setId(1L);
        Vendeg updatedVendeg = new Vendeg("TesztNev", "babamaj", 10, null, null);
        updatedVendeg.setId(1L);

        when(vendegRepository.findById(1L)).thenReturn(Optional.of(vendegToUpdate));
        when(vendegRepository.save(vendegToUpdate)).thenReturn(vendegToUpdate);

        Vendeg resultVendeg = vendegService.updateVendeg(1L, updatedVendeg);

        Assertions.assertEquals(updatedVendeg.getBecenev(), resultVendeg.getBecenev());
    }

    @Test
    void updateVendeg_throws_nincsVendeg_exception() {
        when(vendegRepository.findById(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.updateVendeg(1L, EXPECTED_VENDEG));
    }

    @Test
    void updateVendeg_throws_sikertelen_exception() {
        when(vendegRepository.save(any())).thenThrow(new IllegalArgumentException());
        when(vendegRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_VENDEG));

        Assertions.assertThrows(VendegException.class, () -> vendegService.updateVendeg(1L, EXPECTED_VENDEG));
    }

    @Test
    void findAll_returns_all_vendeg() throws VendegException {
        List<Vendeg> vendegList = new ArrayList<>();
        vendegList.add(EXPECTED_VENDEG_1);
        vendegList.add(EXPECTED_VENDEG_2);
        vendegList.add(EXPECTED_VENDEG_3);
        vendegList.add(EXPECTED_VENDEG_4);

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
        when(vendegRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_VENDEG));

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
        List<Vendeg> vendegList = new ArrayList<>();
        vendegList.add(EXPECTED_VENDEG_1);
        vendegList.add(EXPECTED_VENDEG_2);
        vendegList.add(EXPECTED_VENDEG_3);
        vendegList.add(EXPECTED_VENDEG_4);

        long fogyasztasByVendegId = vendegService.getElfogyasztottMennyisegByVendegId(EXPECTED_VENDEG_1.getId());

        VendegFogyasztasSzerintDto vendegFogyasztasSzerintDto = new VendegFogyasztasSzerintDto("becenev", 1L);

        when(vendegRepository.findAll()).thenReturn(vendegList);
        when(vendegConverter.convertVendegToVFSZDto(EXPECTED_VENDEG_1, fogyasztasByVendegId)).thenReturn(vendegFogyasztasSzerintDto);

        vendegService.getVendegekByElfogyasztottMennyiseg();

        verify(vendegRepository).findAll();
        verify(vendegConverter).convertVendegToVFSZDto(EXPECTED_VENDEG_1, fogyasztasByVendegId);
    }

    @Test
    void getElfogyasztottMennyisegByVendegId_returns_vendeg() throws VendegException {
        Kocsmazas kocsmazas1 = new Kocsmazas(EXPECTED_VENDEG, LocalDateTime.now(), LocalDateTime.now(), null, false);
        Kocsmazas kocsmazas2 = new Kocsmazas(EXPECTED_VENDEG, LocalDateTime.now(), LocalDateTime.now(), null, false);

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
        EXPECTED_VENDEG.setKocsmazasList(kocsmazasList);

        when(vendegRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_VENDEG));

        vendegService.getElfogyasztottMennyisegByVendegId(1L);

        verify(vendegRepository).findById(1L);
    }

    @Test
    void getElfogyasztottMennyisegByVendegId_throws_nincsVendeg_exception() {
        when(vendegRepository.findById(any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(VendegException.class, () -> vendegService.getElfogyasztottMennyisegByVendegId(1L));
    }
}
