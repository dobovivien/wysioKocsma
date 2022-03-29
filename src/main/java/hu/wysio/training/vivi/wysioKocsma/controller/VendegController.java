package hu.wysio.training.vivi.wysioKocsma.controller;

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

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    //get all
    @GetMapping("/getAllVendeg")
    public List<Vendeg> getAllVendeg() {
        return vendegService.findAll();
    }

    //create
    @PostMapping("/createVendeg")
    public ResponseEntity<Vendeg> createVendeg(@RequestBody Vendeg vendegAdat) {
        try {
            Vendeg vendeg = vendegService.createVendeg(vendegAdat);
            return new ResponseEntity<>(vendeg, HttpStatus.CREATED);
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

    //delete
    @DeleteMapping("/deleteVendeg/{id}")
    public void deleteVendeg(@PathVariable Long id, @RequestBody Vendeg vendegAdatok) throws ResourceNotFoundException {
        vendegService.deleteVendeg(vendegAdatok);
    }
}
