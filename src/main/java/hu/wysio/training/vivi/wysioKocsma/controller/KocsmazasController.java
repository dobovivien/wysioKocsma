package hu.wysio.training.vivi.wysioKocsma.controller;

import hu.wysio.training.vivi.wysioKocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysioKocsma.exception.KocsmazasException;
import hu.wysio.training.vivi.wysioKocsma.service.KocsmazasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/kocsmazas")
public class KocsmazasController {

    @Autowired
    KocsmazasService kocsmazasService;

    //create
    @PostMapping("/startKocsmazas/{vendegId}")
    public ResponseEntity<Long> startKocsmazas(@Validated @PathVariable(value = "vendegId") Long vendegId) {
        try {
            long kocsmazasId = kocsmazasService.startKocsmazas(vendegId);
            return new ResponseEntity<>(kocsmazasId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("/finishKocsmazas/{vendegId}")
    public ResponseEntity<Long> finishKocsmazas(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            long finishedKocsmazasId = kocsmazasService.finishKocsmazas(vendegId);
            return new ResponseEntity<>(finishedKocsmazasId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //detoxba kerult
    @PutMapping("/addToDetox/{vendegId}")
    public ResponseEntity<Long> addToDetox(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            kocsmazasService.addToDetox(vendegId);
            return new ResponseEntity<>(vendegId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //alkoholista-e
    @GetMapping("/vendegIsDetoxos/{vendegId}")
    public ResponseEntity<Boolean> vendegIsDetoxos(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            boolean detoxos = kocsmazasService.vendegIsDetoxos(vendegId);
            return new ResponseEntity<>(detoxos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("isAlkoholista/{vendegId}")
    public ResponseEntity<Boolean> isAlkoholista(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            boolean alkoholista = kocsmazasService.isAlkoholista(vendegId);
            return new ResponseEntity<>(alkoholista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("isAlkoholistaWithCriteriaBuilder/{vendegId}")
    public ResponseEntity<List<KocsmazasDto>> isAlkoholistaWithCriteriaBuilder(@PathVariable(value = "vendegId") Long vendegId) throws KocsmazasException {
        List<KocsmazasDto> alkoholistaList = kocsmazasService.isAlkoholistaWithCriteriaBuilder(vendegId);
        if (alkoholistaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(alkoholistaList, HttpStatus.OK);
    }
}
