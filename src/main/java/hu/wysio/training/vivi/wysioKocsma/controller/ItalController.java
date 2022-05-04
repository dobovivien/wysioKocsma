package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ItalException;
import hu.wysio.training.vivi.wysioKocsma.model.Ital;
import hu.wysio.training.vivi.wysioKocsma.service.ItalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/italok")
public class ItalController {

    @Autowired
    ItalService italService;

    //get all
    @GetMapping("/getAllItal")
    public ResponseEntity<List<Ital>> getAllItal() throws ItalException {
        List<Ital> allItal = italService.findAll();
        if (allItal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allItal, HttpStatus.OK);
    }

    //create
    @PostMapping("/createItal")
    public ResponseEntity<Long> createItal(@Validated @RequestBody ItalDto italDto) {
        try {
            long italId = italService.createItal(italDto);
            return new ResponseEntity<>(italId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("/updateItal/{id}")
    public ResponseEntity<Ital> updateItal(@Validated @PathVariable Long id, @RequestBody Ital italAdat) throws ItalException {
        Ital updatedItal = italService.updateItal(id, italAdat);
        if (updatedItal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedItal, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/deleteItal/{id}")
    public void deleteItal(@PathVariable Long id, @RequestBody Ital italAdat) throws ItalException {
        italService.deleteItal(italAdat);
    }
}
