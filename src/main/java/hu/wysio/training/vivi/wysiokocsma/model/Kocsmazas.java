package hu.wysio.training.vivi.wysiokocsma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Kocsmazas extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@NotNull
	private Vendeg vendeg;

	@Column(nullable = false)
	@NotNull
	private LocalDateTime mettol;

	@Column
	private LocalDateTime meddig;

	@OneToMany(mappedBy = "kocsmazas", fetch = FetchType.LAZY)
	private List<Fogyasztas> fogyasztasLista = new ArrayList<>();

	@Column
	private boolean detoxbaKerult;

}
