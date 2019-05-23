package be.vdab.luigi.forms;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class VanTotPrijsFormTest {
	private static final BigDecimal MIN_EEN = BigDecimal.valueOf(-1);
	private Validator validator;
	private VanTotPrijsForm vanTotPrijsForm;

	@Before
	public void before() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		// Onderstaande is echter niet nodig !!!!!
		vanTotPrijsForm = new VanTotPrijsForm(BigDecimal.ONE, BigDecimal.TEN);

	}

	@Test
	public void vanOk() {
		assertTrue(validator.validateValue(VanTotPrijsForm.class, "van", BigDecimal.ONE).isEmpty());
	}

	@Test
	public void vanMoetIngeVuldZijn() {
		assertFalse(validator.validateValue(VanTotPrijsForm.class, "van", null).isEmpty());
	}

	@Test
	public void vanMoetMinstensNulZijn() {
		assertFalse(validator.validateValue(VanTotPrijsForm.class, "van", MIN_EEN).isEmpty());
	}

	@Test
	public void totOk() {
		assertTrue(validator.validateValue(VanTotPrijsForm.class, "tot", BigDecimal.ONE).isEmpty());
	}

	@Test
	public void totMoetIngeVuldZijn() {
		assertFalse(validator.validateValue(VanTotPrijsForm.class, "tot", null).isEmpty());
	}

	@Test
	public void totMoetMinstensNulZijn() {
		assertFalse(validator.validateValue(VanTotPrijsForm.class, "tot", MIN_EEN).isEmpty());
	}

	// Onderstaande code is echter niet nodig.
	@Test
	public void vanGetterTest() {
		assertEquals(BigDecimal.ONE, vanTotPrijsForm.getVan());
	}

	// Onderstaande code is echter niet nodig.
	@Test
	public void totGetterTest() {
		assertEquals(BigDecimal.TEN, vanTotPrijsForm.getTot());
	}
	
}
