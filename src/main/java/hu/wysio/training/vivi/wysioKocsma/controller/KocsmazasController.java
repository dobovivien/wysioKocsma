package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Kocsmazas;
import hu.wysio.training.vivi.wysioKocsma.service.KocsmazasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/kocsmazas")
public class KocsmazasController {

    @Autowired
    KocsmazasService kocsmazasService;

    //get all
    @GetMapping("/getAllKocsmazas")
    public List<Kocsmazas> getAllKocsmazas() {
        return kocsmazasService.findAll();
    }

    //create
    @PostMapping("/createKocsmazas")
    public ResponseEntity<Kocsmazas> createKocsmazas(@RequestBody Kocsmazas kocsmazasAdat) {
        try {
            Kocsmazas kocsmazas = kocsmazasService.createKocsmazas(kocsmazasAdat);
            return new ResponseEntity<>(kocsmazas, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get by id
    @GetMapping("/getKocsmazasById/{id}")
    public ResponseEntity<Kocsmazas> getKocsmazasById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(kocsmazasService.findById(id));
    }

    //update
    @PutMapping("/updateKocsmazas/{id}")
    public ResponseEntity<Kocsmazas> updateKocsmazas(@PathVariable Long id, @RequestBody Kocsmazas kocsmazasAdat) throws ResourceNotFoundException {
        return ResponseEntity.ok(kocsmazasService.updateKocsmazas(id, kocsmazasAdat));
    }

    //delete
    @DeleteMapping("/deleteKocsmazas/{id}")
    public void deleteKocsmazas(@PathVariable Long id, @RequestBody Kocsmazas kocsmazasAdat) throws ResourceNotFoundException {
        kocsmazasService.deleteKocsmazas(kocsmazasAdat);
    }
}
