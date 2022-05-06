package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.converter.VendegConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.exception.VendegException;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.service.VendegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendeg")
public class VendegController {

    @Autowired
    private VendegService vendegService;

    @Autowired
    private VendegConverter vendegConverter;

    @GetMapping("/get-all-vendeg")
    public ResponseEntity<List<Vendeg>> getAllVendeg() throws VendegException {
        List<Vendeg> allVendeg = vendegService.findAll();
        if (allVendeg.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allVendeg, HttpStatus.OK);
    }

    @PostMapping("/create-vendeg")
    public ResponseEntity<Long> createVendeg(@Validated @RequestBody VendegDto vendegDto) {
        try {
            long vendegId = vendegService.createVendeg(vendegDto);
            return new ResponseEntity<>(vendegId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-vendeg-by-id/{id}")
    public ResponseEntity<Vendeg> getVendegById(@PathVariable Long id) throws VendegException {
        Vendeg vendeg = vendegService.findById(id);
        if (vendeg == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vendeg, HttpStatus.OK);
    }

    @PutMapping("/update-vendeg/{id}")
    public ResponseEntity<Vendeg> updateVendeg(@PathVariable Long id, @Validated @RequestBody VendegDto vendegDto) throws VendegException {
        Vendeg updatedVendeg = vendegService.updateVendeg(id, vendegConverter.convertDtoToVendeg(vendegDto));
        if (updatedVendeg == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedVendeg, HttpStatus.OK);
    }

    @GetMapping("/get-vendegek-by-elfogyasztott-mennyiseg")
    public List<VendegFogyasztasSzerintDto> getVendegekByElfogyasztottMennyiseg() throws VendegException {
        return vendegService.getVendegekByElfogyasztottMennyiseg();
    }
}
