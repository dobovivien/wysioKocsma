package hu.wysio.training.vivi.wysioKocsma.model;


public enum Majerosseg {

    BABAMAJ("Babamaj", 0.5),
    TINIMAJ("Tinimaj", 2.5),
    EGYETEMISTAMAJ("Egyetemistamaj", 3),
    GYEMANTMAJ("Gyemantmaj", 4),
    FANNIMAJ("Fannimaj", Double.POSITIVE_INFINITY);

    private final String erosseg;
    private final double erossegFoka;

    Majerosseg(String erosseg, double erossegFoka) {
        this.erosseg = erosseg;
        this.erossegFoka = erossegFoka;
    }

    public String erosseg() {
        return erosseg;
    }

    public double erossegFoka() {
        return erossegFoka;
    }
}
