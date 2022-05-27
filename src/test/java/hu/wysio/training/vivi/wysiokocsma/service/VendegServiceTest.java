package hu.wysio.training.vivi.wysiokocsma.service;

import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.exception.VendegException;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.repository.VendegRepository;
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
class VendegServiceTest {

    public static final long ID = 1L;

    private static final Vendeg EXPECTED_VENDEG = new Vendeg("TesztNev", BABAMAJ, 10, null, null);
    private static final VendegDto VENDEG_DTO = new VendegDto(ID, "TesztNev", BABAMAJ, 10, null, null);

    private static final Vendeg originalVendeg = new Vendeg("TesztNev", BABAMAJ, 10, null, null);

    private static final Vendeg EXPECTED_VENDEG_1 = new Vendeg("TesztNev1", BABAMAJ, 10, null, null);
    private static final Vendeg EXPECTED_VENDEG_2 = new Vendeg("TesztNev2", BABAMAJ, 10, null, null);
    private static final Vendeg EXPECTED_VENDEG_3 = new Vendeg("TesztNev3", BABAMAJ, 10, null, null);
    private static final Vendeg EXPECTED_VENDEG_4 = new Vendeg("TesztNev4", BABAMAJ, 10, null, null);

    private static final Kocsmazas KOCSMAZAS_1 = new Kocsmazas(EXPECTED_VENDEG, LocalDateTime.now(), LocalDateTime.now(), null, false);
    private static final Kocsmazas KOCSMAZAS_2 = new Kocsmazas(EXPECTED_VENDEG, LocalDateTime.now(), LocalDateTime.now(), null, false);

    private static final List<Kocsmazas> KOCSMAZAS_LIST = new ArrayList<>();

    private static final Ital ITAL = new Ital("italNev", 10, 10);

    private static final Fogyasztas FOGYASZTAS_1 = new Fogyasztas(ITAL, 10, KOCSMAZAS_1);
    private static final Fogyasztas FOGYASZTAS_2 = new Fogyasztas(ITAL, 10, KOCSMAZAS_2);

    private static final List<Fogyasztas> FOGYASZTAS_LIST = new ArrayList<>();


    @MockBean
    private VendegConverter vendegConverter;

    @MockBean
    private VendegRepository vendegRepository;

    @Autowired
    private VendegService vendegService;

    @Test
    void createVendeg_returns_vendegId() {
        when(vendegConverter.toEntity(VENDEG_DTO)).thenReturn(EXPECTED_VENDEG);
        when(vendegRepository.save(EXPECTED_VENDEG)).thenReturn(EXPECTED_VENDEG);

        vendegService.createVendeg(VENDEG_DTO);

        verify(vendegConverter).toEntity(VENDEG_DTO);
        verify(vendegRepository).save(EXPECTED_VENDEG);
    }

    @Test
    void updateVendeg_finds_id() throws WysioKocsmaException {
        originalVendeg.setId(ID);
        VENDEG_DTO.setId(ID);

        when(vendegRepository.findById(ID)).thenReturn(Optional.of(originalVendeg));
        when(vendegRepository.save(originalVendeg)).thenReturn(originalVendeg);

        Vendeg resultVendeg = vendegService.updateVendeg(ID, VENDEG_DTO);

        Assertions.assertEquals(VENDEG_DTO.getNev(), resultVendeg.getBecenev());
        
        verify(vendegRepository).findById(ID);
        verify(vendegRepository).save(originalVendeg);
    }

    @Test
    void updateVendeg_throws_nincsVendege_exception() {
        when(vendegRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(VendegException.class, () -> vendegService.updateVendeg(ID, VENDEG_DTO));

        verify(vendegRepository).findById(any());
    }

    @Test
    void findAll_returns_all_vendeg() {
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
    void findById_returns_vendeg_by_id() throws WysioKocsmaException {
        when(vendegRepository.findById(ID)).thenReturn(Optional.of(EXPECTED_VENDEG));

        vendegService.getById(ID);

        verify(vendegRepository).findById(ID);
    }

    @Test
    void findById_throws_nincsVendege_exception() {
        when(vendegRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(VendegException.class, () -> vendegService.getById(ID));

        verify(vendegRepository).findById(any());
    }

    @Test
    void getVendegekByElfogyasztottMennyiseg_returns_all_vendeg() {
        List<Vendeg> vendegList = new ArrayList<>();
        vendegList.add(EXPECTED_VENDEG);
        vendegList.add(EXPECTED_VENDEG_1);
        vendegList.add(EXPECTED_VENDEG_2);

        FOGYASZTAS_LIST.add(FOGYASZTAS_1);
        FOGYASZTAS_LIST.add(FOGYASZTAS_2);

        KOCSMAZAS_1.setFogyasztasLista(FOGYASZTAS_LIST);
        KOCSMAZAS_2.setFogyasztasLista(FOGYASZTAS_LIST);

        KOCSMAZAS_LIST.add(KOCSMAZAS_1);
        KOCSMAZAS_LIST.add(KOCSMAZAS_2);

        EXPECTED_VENDEG.setKocsmazasList(KOCSMAZAS_LIST);
        EXPECTED_VENDEG_1.setKocsmazasList(KOCSMAZAS_LIST);
        EXPECTED_VENDEG_2.setKocsmazasList(KOCSMAZAS_LIST);

        long fogyasztasByVendeg = vendegService.getElfogyasztottMennyisegByVendeg(EXPECTED_VENDEG);

        VendegFogyasztasSzerintDto vendegFogyasztasSzerintDto = new VendegFogyasztasSzerintDto("becenev", ID);

        when(vendegRepository.findAll()).thenReturn(vendegList);
        when(vendegConverter.toVendegFogyasztasSzerintDto(EXPECTED_VENDEG.getBecenev(), fogyasztasByVendeg)).thenReturn(vendegFogyasztasSzerintDto);

        vendegService.getVendegekByElfogyasztottMennyiseg();

        Assertions.assertEquals(160, vendegService.getElfogyasztottMennyisegByVendeg(EXPECTED_VENDEG));

        verify(vendegRepository).findAll();
        verify(vendegConverter).toVendegFogyasztasSzerintDto(EXPECTED_VENDEG.getBecenev(), fogyasztasByVendeg);
    }

    @Test
    void getElfogyasztottMennyisegByVendeg_returns_elfogyasztottMennyiseg() {
        FOGYASZTAS_LIST.add(FOGYASZTAS_1);
        FOGYASZTAS_LIST.add(FOGYASZTAS_2);

        KOCSMAZAS_1.setFogyasztasLista(FOGYASZTAS_LIST);
        KOCSMAZAS_2.setFogyasztasLista(FOGYASZTAS_LIST);

        KOCSMAZAS_LIST.add(KOCSMAZAS_1);
        KOCSMAZAS_LIST.add(KOCSMAZAS_2);

        EXPECTED_VENDEG.setKocsmazasList(KOCSMAZAS_LIST);

        Assertions.assertEquals(40, vendegService.getElfogyasztottMennyisegByVendeg(EXPECTED_VENDEG));
    }
}
