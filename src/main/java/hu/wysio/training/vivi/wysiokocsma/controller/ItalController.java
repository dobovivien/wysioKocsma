package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.service.ItalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/italok")
public class ItalController {

    @Autowired
    private ItalService italService;


    @GetMapping
    public ResponseEntity<List<Ital>> getAllItal() {
        return new ResponseEntity<>(italService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createItal(@Validated @RequestBody ItalDto italDto) {
        return new ResponseEntity<>(italService.createItal(italDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ital> updateItal(@PathVariable Long id, @Validated @RequestBody ItalDto italDto) {
        return new ResponseEntity<>(italService.updateItal(id, italDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItal(@PathVariable Long id) {
        italService.deleteItal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
