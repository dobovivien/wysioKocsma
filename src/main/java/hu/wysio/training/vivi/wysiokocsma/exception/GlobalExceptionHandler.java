package hu.wysio.training.vivi.wysiokocsma.exception;

import hu.wysio.training.vivi.wysiokocsma.model.ExceptionMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleWysioKocsmaException(WysioKocsmaException ex) {

        ExceptionMessage exceptionMessage = ex.exceptionMessage;
        HttpStatus httpStatus = exceptionMessage.getHttpStatus();

        ErrorMessage errorMessage = new ErrorMessage(
                httpStatus.toString(),
                LocalDateTime.now(),
                exceptionMessage.getMessage());

        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException() {

        ErrorMessage message = new ErrorMessage(
                ExceptionMessage.ISMERETLEN_HIBA.getHttpStatus().toString(),
                LocalDateTime.now(),
                ExceptionMessage.ISMERETLEN_HIBA.getMessage());

        return new ResponseEntity<>(message, ExceptionMessage.ISMERETLEN_HIBA.getHttpStatus());
    }
}
