package hu.wysio.training.vivi.wysiokocsma.exception;

import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;

public class KocsmazasException extends WysioKocsmaException {

    public KocsmazasException(ExceptionMessage message) {
        super(message);
    }
}
