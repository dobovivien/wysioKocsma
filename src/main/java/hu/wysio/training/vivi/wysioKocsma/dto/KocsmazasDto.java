package hu.wysio.training.vivi.wysioKocsma.dto;

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

    private List<FogyasztasDto> fogyasztasLista;
    private boolean detoxbaKerult;
}
