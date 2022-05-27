package hu.wysio.training.vivi.wysiokocsma.exception;

import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;

public class FogyasztasException extends WysioKocsmaException {

    public FogyasztasException(ExceptionMessage message) {
        super(message);
    }
}
