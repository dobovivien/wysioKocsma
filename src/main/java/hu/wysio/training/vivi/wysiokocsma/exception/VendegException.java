package hu.wysio.training.vivi.wysiokocsma.exception;

import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;

public class VendegException extends WysioKocsmaException {

    public VendegException(ExceptionMessage message) {
        super(message);
    }
}
