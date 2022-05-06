package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysiokocsma.exception.BunyoException;
import hu.wysio.training.vivi.wysiokocsma.service.BunyoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bunyo")
public class BunyoController {

    @Autowired
    private BunyoService bunyoService;

    @GetMapping("/get-all-bunyo")
    public ResponseEntity<List<TabellaDto>> getAllBunyoByNyertesek() throws BunyoException {
        List<TabellaDto> allBunyo = bunyoService.getTabellaEredmeny();
        if (allBunyo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allBunyo, HttpStatus.OK);
    }

    @PostMapping("/start-bunyo")
    public ResponseEntity<Long> startBunyo() {
        try {
            long bunyoId = bunyoService.startBunyo();
            return new ResponseEntity<>(bunyoId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
