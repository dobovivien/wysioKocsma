package hu.wysio.training.vivi.wysiokocsma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BunyoDto implements Serializable {

    private String nyertes;
    private Long nyertesId;
}
