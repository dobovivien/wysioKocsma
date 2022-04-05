package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.BunyoDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
import hu.wysio.training.vivi.wysioKocsma.model.Bunyo;
import hu.wysio.training.vivi.wysioKocsma.service.BunyoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/bunyok")
public class BunyoController {

    @Autowired
    BunyoService bunyoService;

    //get all
    @GetMapping("/getAllBunyo")
    public List<Bunyo> getAllBunyo() {
        return bunyoService.findAll();
    }

    //create
    @PostMapping("/createBunyo")
    public ResponseEntity<Long> createBunyo(@RequestBody BunyoDto bunyoDto) {
        try {
            long bunyoId = bunyoService.createBunyo(bunyoDto);
            return new ResponseEntity<>(bunyoId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("/updateBunyo/{id}")
    public ResponseEntity<Bunyo> updateBunyo(@PathVariable Long id, @RequestBody Bunyo bunyoAdat) throws ResourceNotFoundException {
        return ResponseEntity.ok(bunyoService.updateBunyo(id, bunyoAdat));
    }
}
