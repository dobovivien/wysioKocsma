package hu.wysio.training.vivi.wysiokocsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FogyasztasDto implements Serializable {

    @NotNull
    private Long kocsmazasId;

    @NotNull
    private Long italId;

    @NotNull
    @Positive
    private Integer elfogyasztottMennyiseg;

}
