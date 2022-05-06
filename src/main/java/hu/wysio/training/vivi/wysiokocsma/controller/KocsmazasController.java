package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysiokocsma.exception.KocsmazasException;
import hu.wysio.training.vivi.wysiokocsma.service.KocsmazasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kocsmazas")
public class KocsmazasController {

    @Autowired
    private KocsmazasService kocsmazasService;

    @PostMapping("/start-kocsmazas/{vendegId}")
    public ResponseEntity<Long> startKocsmazas(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            long kocsmazasId = kocsmazasService.startKocsmazas(vendegId);
            return new ResponseEntity<>(kocsmazasId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/finish-kocsmazas/{vendegId}")
    public ResponseEntity<Long> finishKocsmazas(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            long finishedKocsmazasId = kocsmazasService.finishKocsmazas(vendegId);
            return new ResponseEntity<>(finishedKocsmazasId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/add-to-detox/{vendegId}")
    public ResponseEntity<Long> addToDetox(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            kocsmazasService.addToDetox(vendegId);
            return new ResponseEntity<>(vendegId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vendeg-is-detoxos/{vendegId}")
    public ResponseEntity<Boolean> vendegIsDetoxos(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            boolean detoxos = kocsmazasService.vendegIsDetoxos(vendegId);
            return new ResponseEntity<>(detoxos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("is-alkoholista/{vendegId}")
    public ResponseEntity<Boolean> isAlkoholista(@PathVariable(value = "vendegId") Long vendegId) {
        try {
            boolean alkoholista = kocsmazasService.isAlkoholista(vendegId);
            return new ResponseEntity<>(alkoholista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("is-alkoholista-with-criteria-builder/{vendegId}")
    public ResponseEntity<List<KocsmazasDto>> isAlkoholistaWithCriteriaBuilder(@PathVariable(value = "vendegId") Long vendegId) throws KocsmazasException {
        List<KocsmazasDto> alkoholistaList = kocsmazasService.isAlkoholistaWithCriteriaBuilder(vendegId);
        if (alkoholistaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(alkoholistaList, HttpStatus.OK);
    }
}
