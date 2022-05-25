package hu.wysio.training.vivi.wysiokocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {

	SIKERTELEN("Sikertelen művelet.", HttpStatus.INTERNAL_SERVER_ERROR),
	NINCS_FOGYASZTAS("Nincs ilyen fogyasztás ezzel az id-val.", HttpStatus.NOT_FOUND),
	NINCS_ITAL("Nincs ilyen ital ezzel az id-val.", HttpStatus.NOT_FOUND),
	NINCS_VENDEG("Nincs ilyen vendég ezzel az id-val.", HttpStatus.NOT_FOUND),
	NINCS_KOCSMAZAS("Nincs ilyen kocsmázás ezzel az id-val.", HttpStatus.NOT_FOUND),
	NINCS_BUNYO("Nincs ilyen bunyó ezzel az id-val.", HttpStatus.NOT_FOUND),
	ISMERETLEN_HIBA("Ismeretlen hiba!", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String message;
	private final HttpStatus httpStatus;
}
