package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.service.FogyasztasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/fogyasztas")
public class FogyasztasController {

    @Autowired
    FogyasztasService fogyasztasService;

    //create
    @PostMapping("/createFogyasztas")
    public ResponseEntity<Long> createFogyasztas(@RequestBody FogyasztasDto fogyasztasDto) {
        try {
            long fogyasztasId = fogyasztasService.createFogyasztas(fogyasztasDto);
            return new ResponseEntity<>(fogyasztasId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("/updateFogyasztas/{id}")
    public ResponseEntity<Fogyasztas> updateFogyasztas(@PathVariable Long id, @RequestBody Fogyasztas fogyasztasAdat) throws ResourceNotFoundException {
        return ResponseEntity.ok(fogyasztasService.updateFogyasztas(id, fogyasztasAdat));
    }
}
