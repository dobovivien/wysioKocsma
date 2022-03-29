package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysioKocsma.service.FogyasztasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/fogyasztas")
public class FogyasztasController {

    @Autowired
    FogyasztasService fogyasztasService;

    //get all
    @GetMapping("/getAllFogyasztas")
    public List<Fogyasztas> getAllFogyasztas() {
        return fogyasztasService.findAll();
    }

    //create
    @PostMapping("/createFogyasztas")
    public ResponseEntity<Fogyasztas> createFogyasztas(@RequestBody Fogyasztas fogyasztasAdat) {
        try {
            Fogyasztas fogyasztas = fogyasztasService.createFogyasztas(fogyasztasAdat);
            return new ResponseEntity<>(fogyasztas, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get by id
    @GetMapping("/getFogyasztasById/{id}")
    public ResponseEntity<Fogyasztas> getFogyasztasById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(fogyasztasService.findById(id));
    }

    //update
    @PutMapping("/updateFogyasztas/{id}")
    public ResponseEntity<Fogyasztas> updateFogyasztas(@PathVariable Long id, @RequestBody Fogyasztas fogyasztasAdat) throws ResourceNotFoundException {
        return ResponseEntity.ok(fogyasztasService.updateFogyasztas(id, fogyasztasAdat));
    }

    //delete
    @DeleteMapping("/deleteFogyasztas/{id}")
    public void deleteFogyasztas(@PathVariable Long id, @RequestBody Fogyasztas fogyasztasAdat) throws ResourceNotFoundException {
        fogyasztasService.deleteFogyasztas(fogyasztasAdat);
    }
}
