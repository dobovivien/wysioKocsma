package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.VendegDto;
import hu.wysio.training.vivi.wysioKocsma.dto.VendegFogyasztasSzerintDto;
import hu.wysio.training.vivi.wysioKocsma.exception.VendegException;
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
    public ResponseEntity<List<Vendeg>> getAllVendeg() throws VendegException {
        List<Vendeg> allVendeg = vendegService.findAll();
        if (allVendeg.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allVendeg, HttpStatus.OK);
    }

    //create
    @PostMapping("/createVendeg")
    public ResponseEntity<Long> createVendeg(@RequestBody VendegDto vendegDto) {
        try {
            long vendegId = vendegService.createVendeg(vendegDto);
            return new ResponseEntity<>(vendegId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get by id
    @GetMapping("/getVendegById/{id}")
    public ResponseEntity<Vendeg> getVendegById(@PathVariable Long id) throws VendegException {
        Vendeg vendeg = vendegService.findById(id);
        if (vendeg == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vendeg, HttpStatus.OK);
    }

    //update
    @PutMapping("/updateVendeg/{id}")
    public ResponseEntity<Vendeg> updateVendeg(@PathVariable Long id, @RequestBody Vendeg vendegAdatok) throws VendegException {
        Vendeg updatedVendeg = vendegService.updateVendeg(id, vendegAdatok);
        if (updatedVendeg == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedVendeg, HttpStatus.OK);
    }

    @GetMapping("/getVendegekByElfogyasztottMennyiseg")
    public List<VendegFogyasztasSzerintDto> getVendegekByElfogyasztottMennyiseg() throws VendegException {
        return vendegService.getVendegekByElfogyasztottMennyiseg();
    }
}
