package hu.wysio.training.vivi.wysiokocsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItalDto implements Serializable {

    @NotEmpty
    private String italNev;

    @NotNull
    @Positive
    private Integer alkoholTartalom;

    @NotNull
    @Positive
    private Integer adagMennyisege;
}
