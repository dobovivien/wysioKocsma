package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import hu.wysio.training.vivi.wysioKocsma.service.ItalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/italok")
public class ItalController {

    @Autowired
    ItalService italService;

    //get all
    @GetMapping("/getAllItal")
    public List<Ital> getAllItal() {
        return italService.findAll();
    }

    //create
    @PostMapping("/createItal")
    public ResponseEntity<Ital> createItal(@RequestBody Ital italAdat) {
        try {
            Ital ital = italService.createItal(italAdat);
            return new ResponseEntity<>(ital, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get by id
    @GetMapping("/getItalById/{id}")
    public ResponseEntity<Ital> getItalById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(italService.findById(id));
    }

    //update
    @PutMapping("/updateItal/{id}")
    public ResponseEntity<Ital> updateItal(@PathVariable Long id, @RequestBody Ital italAdat) throws ResourceNotFoundException {
        return ResponseEntity.ok(italService.updateItal(id, italAdat));
    }

    //delete
    @DeleteMapping("/deleteItal/{id}")
    public void deleteItal(@PathVariable Long id, @RequestBody Ital italAdat) throws ResourceNotFoundException {
        italService.deleteItal(italAdat);
    }
}
