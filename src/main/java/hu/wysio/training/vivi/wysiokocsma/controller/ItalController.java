package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.converter.ItalConverter;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalDto;
import hu.wysio.training.vivi.wysiokocsma.exception.ItalException;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.service.ItalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ital")
public class ItalController {

    @Autowired
    private ItalService italService;

    @Autowired
    private ItalConverter italConverter;

    @GetMapping("/get-all-ital")
    public ResponseEntity<List<Ital>> getAllItal() throws ItalException {
        List<Ital> allItal = italService.findAll();
        if (allItal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allItal, HttpStatus.OK);
    }

    @PostMapping("/create-ital")
    public ResponseEntity<Long> createItal(@Validated @RequestBody ItalDto italDto) {
        try {
            long italId = italService.createItal(italDto);
            return new ResponseEntity<>(italId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-ital/{id}")
    public ResponseEntity<Ital> updateItal(
            @PathVariable Long id,
            @Validated @RequestBody ItalDto italDto) throws ItalException {
        Ital updatedItal = italService.updateItal(id, italConverter.convertDtoToItal(italDto));
        if (updatedItal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedItal, HttpStatus.OK);
    }

    @DeleteMapping("/delete-ital/{id}")
    public void deleteItal(@PathVariable Long id, @RequestBody ItalDto italDto) throws ItalException {
        italService.deleteItal(italConverter.convertDtoToItal(italDto));
    }
}
