package hu.wysio.training.vivi.wysiokocsma.converter;

import hu.wysio.training.vivi.wysiokocsma.dto.FogyasztasDto;
import hu.wysio.training.vivi.wysiokocsma.model.Fogyasztas;
import hu.wysio.training.vivi.wysiokocsma.model.Ital;
import hu.wysio.training.vivi.wysiokocsma.model.Kocsmazas;
import org.springframework.stereotype.Component;

@Component
public class FogyasztasConverter {

    public Fogyasztas toFogyasztas(FogyasztasDto fogyasztasDto) {
        Ital ital = new Ital();
        ital.setId(fogyasztasDto.getItalId());

        Kocsmazas kocsmazas = new Kocsmazas();
        kocsmazas.setId(fogyasztasDto.getKocsmazasId());

        Fogyasztas fogyasztas = new Fogyasztas();
        fogyasztas.setElfogyasztottMennyiseg(fogyasztasDto.getElfogyasztottMennyiseg());
        fogyasztas.setItal(ital);
        fogyasztas.setKocsmazas(kocsmazas);

        return fogyasztas;
    }
}
