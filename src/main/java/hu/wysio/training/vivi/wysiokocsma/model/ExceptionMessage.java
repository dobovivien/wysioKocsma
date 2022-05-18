package hu.wysio.training.vivi.wysiokocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {

	SIKERTELEN("Sikertelen művelet."),
	NINCS_FOGYASZTAS("Nincs ilyen fogyasztás az alabbi id-val: "),
	NINCS_ITAL("Nincs ilyen ital az alabbi id-val: "),
	NINCS_VENDEG("Nincs ilyen vendég az alabbi id-val: "),
	NINCS_KOCSMAZAS("Nincs ilyen kocsmázás az alabbi id-val: "),
	NINCS_BUNYO("Nincs ilyen bunyó az alabbi id-val: ");

	private final String message;
}
