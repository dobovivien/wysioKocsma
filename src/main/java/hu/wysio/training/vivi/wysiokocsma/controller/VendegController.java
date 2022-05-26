package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysiokocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.model.Vendeg;
import hu.wysio.training.vivi.wysiokocsma.service.VendegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendegek")
public class VendegController {

    @Autowired
    private VendegService vendegService;
    

    @GetMapping
    public ResponseEntity<List<Vendeg>> getAllVendeg() {
        return new ResponseEntity<>(vendegService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createVendeg(@Validated @RequestBody VendegDto vendegDto) {
        return new ResponseEntity<>(vendegService.createVendeg(vendegDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendeg> getVendegById(@PathVariable Long id) throws WysioKocsmaException {
        return new ResponseEntity<>(vendegService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendeg> updateVendeg(@PathVariable Long id, @Validated @RequestBody VendegDto vendegDto) throws WysioKocsmaException {
        return new ResponseEntity<>(vendegService.updateVendeg(id, vendegDto), HttpStatus.OK);
    }

    @GetMapping("/elfogyasztott-mennyiseg")
    public ResponseEntity<List<VendegFogyasztasSzerintDto>> getVendegekByElfogyasztottMennyiseg() {
        return new ResponseEntity<>(vendegService.getVendegekByElfogyasztottMennyiseg(), HttpStatus.OK);
    }
}
