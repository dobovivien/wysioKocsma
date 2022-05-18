package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.KocsmazasDto;
import hu.wysio.training.vivi.wysiokocsma.exception.WysioKocsmaException;
import hu.wysio.training.vivi.wysiokocsma.service.KocsmazasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kocsmazasok")
public class KocsmazasController {

    @Autowired
    private KocsmazasService kocsmazasService;


    @PostMapping("/{id}")
    public ResponseEntity<Long> startKocsmazas(@PathVariable(value = "id") Long id) throws WysioKocsmaException {
        return new ResponseEntity<>(kocsmazasService.startKocsmazas(id), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> finishKocsmazas(@PathVariable(value = "id") Long id) throws WysioKocsmaException {
        return new ResponseEntity<>(kocsmazasService.finishKocsmazas(id), HttpStatus.OK);
    }

    @PutMapping("/detox/{id}")
    public ResponseEntity<Long> addToDetox(@PathVariable(value = "id") Long id) throws WysioKocsmaException {
        kocsmazasService.addToDetox(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/detox/{id}")
    public ResponseEntity<Boolean> vendegIsDetoxos(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(kocsmazasService.vendegIsDetoxos(id), HttpStatus.OK);
    }

    @GetMapping("/alkoholista/{id}")
    public ResponseEntity<Boolean> isAlkoholista(@PathVariable(value = "id") Long id) throws WysioKocsmaException {
        return new ResponseEntity<>(kocsmazasService.isAlkoholista(id), HttpStatus.OK);
    }

    @GetMapping("/alkoholista-with-criteria-builder/{id}")
    public ResponseEntity<List<KocsmazasDto>> isAlkoholistaWithCriteriaBuilder(@PathVariable(value = "id") Long id) throws WysioKocsmaException {
        return new ResponseEntity<>(kocsmazasService.isAlkoholistaWithCriteriaBuilder(id), HttpStatus.OK);
    }
}
