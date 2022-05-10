package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.converter.FogyasztasConverter;
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
@RequestMapping("/fogyasztas")
public class FogyasztasController {

    @Autowired
    private FogyasztasService fogyasztasService;

    @Autowired
    private FogyasztasConverter fogyasztasConverter;

    @PostMapping("/create-fogyasztas")
    public ResponseEntity<Long> createFogyasztas(@Validated @RequestBody FogyasztasDto fogyasztasDto) {
        try {
            long fogyasztasId = fogyasztasService.createFogyasztas(fogyasztasDto).getId();
            return new ResponseEntity<>(fogyasztasId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-fogyasztas/{id}")
    public ResponseEntity<Fogyasztas> updateFogyasztas(
            @PathVariable Long id,
            @Validated @RequestBody FogyasztasDto fogyasztasDto) {
        try {
            Fogyasztas fogyasztas = fogyasztasService.updateFogyasztas(id, fogyasztasConverter.toFogyasztas(fogyasztasDto));
            return new ResponseEntity<>(fogyasztas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-top-ital")
    public ResponseEntity<List<ItalRangsorDto>> getTopItal() {
        List<ItalRangsorDto> italRangsorDtoList = fogyasztasService.getLegtobbetFogyasztottItal();
        if (italRangsorDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(italRangsorDtoList, HttpStatus.OK);
    }
}
