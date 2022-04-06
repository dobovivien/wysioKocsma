package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysioKocsma.service.BunyoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/bunyok")
public class BunyoController {

    @Autowired
    BunyoService bunyoService;

    //bunyo tabella lekerdezese
    @GetMapping("/getAllBunyo")
    public List<TabellaDto> getAllBunyoByNyertesek() {
        return bunyoService.getTabellaEredmeny();
    }

    //create
    @PostMapping("/startBunyo")
    public ResponseEntity<Long> startBunyo() {
        try {
            long bunyoId = bunyoService.startBunyo();
            return new ResponseEntity<>(bunyoId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    //update
//    @PutMapping("/updateBunyo/{id}")
//    public ResponseEntity<Bunyo> finishBunyo(@PathVariable Long id, @RequestBody Bunyo bunyoAdat) throws ResourceNotFoundException {
//        return ResponseEntity.ok(bunyoService.finishBunyo(id, bunyoAdat));
//    }
}
