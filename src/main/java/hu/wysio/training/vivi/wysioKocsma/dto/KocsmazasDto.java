package hu.wysio.training.vivi.wysioKocsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KocsmazasDto implements Serializable {

    LocalDateTime mettol;
    LocalDateTime meddig;
    private List<Long> fogyasztasLista;
    private boolean detoxbaKerult;
}
