package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.dto.ItalRangsorDto;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.service.FogyasztasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fogyasztasok")
public class FogyasztasController {

    @Autowired
    private FogyasztasService fogyasztasService;


    @PostMapping
    public ResponseEntity<Long> createFogyasztas(@Validated @RequestBody FogyasztasDto fogyasztasDto) {
        long fogyasztasId = fogyasztasService.createFogyasztas(fogyasztasDto).getId();
        return new ResponseEntity<>(fogyasztasId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fogyasztas> updateFogyasztas(@PathVariable Long id, @Validated @RequestBody FogyasztasDto fogyasztasDto) {
        Fogyasztas fogyasztas = fogyasztasService.updateFogyasztas(id, fogyasztasDto);
        return new ResponseEntity<>(fogyasztas, HttpStatus.OK);
    }

    @GetMapping("/top-ital")
    public ResponseEntity<List<ItalRangsorDto>> getTopItal() {
        return new ResponseEntity<>(fogyasztasService.getLegtobbetFogyasztottItal(), HttpStatus.OK);

    }
}
