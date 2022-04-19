package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysioKocsma.exception.ResourceNotFoundException;
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

    //create
    @PostMapping("/startKocsmazas/{vendegId}")
    public ResponseEntity<Long> startKocsmazas(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            long kocsmazasId = kocsmazasService.startKocsmazas(vendegId);
            return new ResponseEntity<>(kocsmazasId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("/finishKocsmazas/{vendegId}")
    public ResponseEntity<Long> finishKocsmazas(@PathVariable(value = "vendegId") Long vendegId) throws ResourceNotFoundException {
        return ResponseEntity.ok(kocsmazasService.finishKocsmazas(vendegId));
    }

    //detoxba kerult
    @PutMapping("/addToDetox/{vendegId}")
    public void addToDetox(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            kocsmazasService.addToDetox(vendegId);
        } catch (Exception e) {

        }
    }

    //alkoholista-e
    @GetMapping("/vendegIsDetoxos/{vendegId}")
    public boolean vendegIsDetoxos(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            return kocsmazasService.vendegIsDetoxos(vendegId);
        } catch (Exception e) {

        }
        return false;
    }

    @GetMapping("isAlkoholista/{vendegId}")
    public boolean isAlkoholista(@PathVariable(value = "vendegId") Long vendegId) {
        return kocsmazasService.isAlkoholista(vendegId);
    }

    @GetMapping("isAlkoholistaWithCriteriaBuilder/{vendegId}")
    public List<KocsmazasDto> isAlkoholistaWithCriteriaBuilder(@PathVariable(value = "vendegId") Long vendegId) {
        return kocsmazasService.isAlkoholistaWithCriteriaBuilder(vendegId);
    }
}
