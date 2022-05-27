package hu.wysio.training.vivi.wysiokocsma.exception;

import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;

public class WysioKocsmaException extends Exception {

    final ExceptionMessage exceptionMessage;

    public WysioKocsmaException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }
}