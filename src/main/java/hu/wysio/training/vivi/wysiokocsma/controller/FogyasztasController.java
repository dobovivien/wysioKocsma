package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.exception.FogyasztasException;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.service.FogyasztasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController("/fogyasztas")
public class FogyasztasController {

    @Autowired
    FogyasztasService fogyasztasService;

    //create
    @PostMapping("/createFogyasztas")
    public ResponseEntity<Long> createFogyasztas(@Validated @RequestBody FogyasztasDto fogyasztasDto) {
        try {
            long fogyasztasId = fogyasztasService.createFogyasztas(fogyasztasDto).getId();
            return new ResponseEntity<>(fogyasztasId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update
    @PutMapping("/updateFogyasztas/{id}")
    public ResponseEntity<Fogyasztas> updateFogyasztas(@Validated @PathVariable Long id, @RequestBody Fogyasztas fogyasztasAdat) throws FogyasztasException {
        try {
            Fogyasztas fogyasztas = fogyasztasService.updateFogyasztas(id, fogyasztasAdat);
            return new ResponseEntity<>(fogyasztas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTopItal")
    public ResponseEntity<List<ItalRangsorDto>> getTopItal() {
        List<ItalRangsorDto> italRangsorDtoList = fogyasztasService.getLegtobbetFogyasztottItal();
        if (italRangsorDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(italRangsorDtoList, HttpStatus.OK);
    }
}
