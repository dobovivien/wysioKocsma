package com.example.wysioKocsma.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    public static final String ARRAY_TYPE = "com.example.wysioKocsma.hybernateType.ArrayType";
}
