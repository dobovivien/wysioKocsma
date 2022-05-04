package hu.wysio.training.vivi.wysioKocsma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Majerosseg {

    BABAMAJ("Babamaj", 0.5),
    TINIMAJ("Tinimaj", 2.5),
    EGYETEMISTAMAJ("Egyetemistamaj", 3),
    GYEMANTMAJ("Gyemantmaj", 4),
    FANNIMAJ("Fannimaj", Double.POSITIVE_INFINITY);

    private final String erosseg;
    private final double erossegFoka;
}
