package hu.wysio.training.vivi.wysiokocsma.controller;

import hu.wysio.training.vivi.wysiokocsma.dto.TabellaDto;
import hu.wysio.training.vivi.wysiokocsma.service.BunyoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bunyok")
public class BunyoController {

    @Autowired
    private BunyoService bunyoService;


    @GetMapping
    public ResponseEntity<List<TabellaDto>> getAllBunyoByNyertesek() {
        return new ResponseEntity<>(bunyoService.getTabellaEredmeny(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> startBunyo() {
        return new ResponseEntity<>(bunyoService.startBunyo(), HttpStatus.CREATED);
    }
}
