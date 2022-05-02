package hu.wysio.training.vivi.wysioKocsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendegFogyasztasSzerintDto implements Serializable {
    
    private String becenev;
    private long elfogyasztottMennyiseg;
}
