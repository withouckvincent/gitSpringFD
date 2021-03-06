package be.vdab.luigi.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.NumberFormat;

public class Pizza {

	private final long id;
	@NotBlank private final String naam;
	@NumberFormat(pattern = "0.00") @NotNull @PositiveOrZero private final BigDecimal prijs;
	private final boolean pikant;
 
	public Pizza(long id, String naam, BigDecimal prijs, boolean pikant) {
		this.id = id;
		this.naam = naam;
		this.prijs = prijs;
		this.pikant = pikant;
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public boolean isPikant() {
		return pikant;
	}

}
