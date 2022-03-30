package hu.wysio.training.vivi.wysioKocsma.dto;

import hu.wysio.training.vivi.wysioKocsma.model.Fogyasztas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KocsmazasDto implements Serializable {

    private List<Fogyasztas> fogyasztasLista;
    private boolean detoxbaKerult;
}
