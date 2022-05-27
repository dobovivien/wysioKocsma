package hu.wysio.training.vivi.wysiokocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {

	NINCS_FOGYASZTAS("Nincs ilyen fogyasztás a megadott id-val.", HttpStatus.NOT_FOUND),
	NINCS_ITAL("Nincs ilyen ital a megadott id-val.", HttpStatus.NOT_FOUND),
	NINCS_VENDEG("Nincs ilyen vendég a megadott id-val.", HttpStatus.NOT_FOUND),
	NINCS_KOCSMAZAS("Nincs ilyen kocsmázás a megadott id-val.", HttpStatus.NOT_FOUND),
	ISMERETLEN_HIBA("Ismeretlen hiba!", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String message;
	private final HttpStatus httpStatus;
}
