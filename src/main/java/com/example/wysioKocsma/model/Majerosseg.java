package com.example.wysioKocsma.model;


public enum Majerosseg {

    BABAMAJ ("Babamaj", 0.5),
    TINIMAJ ("Tinimaj", 2.5),
    EGYETEMISTAMAJ ("Egyetemistamaj", 3),
    GYEMANTMAJ ("Gyemantmaj", 4),
    FANNIMAJ ("Fannimaj", Double.POSITIVE_INFINITY);

    Majerosseg(String erosseg, double erossegFoka) {
    }
}
