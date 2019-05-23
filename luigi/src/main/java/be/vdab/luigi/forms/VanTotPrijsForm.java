package be.vdab.luigi.forms;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class VanTotPrijsForm {

	@NotNull @PositiveOrZero private final BigDecimal van;
	@NotNull @PositiveOrZero private final BigDecimal tot;
	
	@Valid public VanTotPrijsForm(BigDecimal van, BigDecimal tot) {
		this.van = van;
		this.tot = tot;
	}

	public BigDecimal getVan() {
		return van;
	}

	public BigDecimal getTot() {
		return tot;
	}
	
}
