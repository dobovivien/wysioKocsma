package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Vendeg;
import hu.wysio.training.vivi.wysioKocsma.service.VendegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/vendegek")
public class VendegController {

    @Autowired
    VendegService vendegService;

    //get all
    @GetMapping("/getAllVendeg")
    public List<Vendeg> getAllVendeg() {
        return vendegService.findAll();
    }

    //create
    @PostMapping("/createVendeg")
    public ResponseEntity<Long> createVendeg(@RequestBody VendegDto vendegDto) {
        try {
            long vendegId = vendegService.createVendeg(vendegDto);
            return new ResponseEntity<>(vendegId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get by id
    @GetMapping("/getVendegById/{id}")
    public ResponseEntity<Vendeg> getVendegById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(vendegService.findById(id));
    }

    //update
    @PutMapping("/updateVendeg/{id}")
    public ResponseEntity<Vendeg> updateVendeg(@PathVariable Long id, @RequestBody Vendeg vendegAdatok) throws ResourceNotFoundException {
        return ResponseEntity.ok(vendegService.updateVendeg(id, vendegAdatok));
    }

    @GetMapping("/getVendegekByElfogyasztottMennyiseg")
    public List<VendegFogyasztasSzerintDto> getVendegekByElfogyasztottMennyiseg() {
        return vendegService.getVendegekByElfogyasztottMennyiseg();
    }
}
