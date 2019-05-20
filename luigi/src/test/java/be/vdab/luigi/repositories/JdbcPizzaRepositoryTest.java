package be.vdab.luigi.repositories;

import be.vdab.luigi.exceptions.PizzaNietGevondenException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.luigi.domain.Pizza;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JdbcPizzaRepository.class)
@Sql("/insertPizzas.sql")
public class JdbcPizzaRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	private static final String PIZZAS = "pizzas";
	@Autowired
	private JdbcPizzaRepository repository;

	@Test
	public void findAantal() {
		assertEquals(super.countRowsInTable(PIZZAS), repository.findAantalPizzas());
	}

	@Test
	public void findAll() {
		List<Pizza> pizzas = repository.findAll();
		assertEquals(super.countRowsInTable(PIZZAS), pizzas.size());
		pizzas.stream().map(pizza -> pizza.getId()).reduce((vorigeId, id) -> {
			assertTrue(id > vorigeId);
			return id;
		});
	}

	@Test
	public void create() {
		int aantalPizzasVoorInsert = super.countRowsInTable(PIZZAS);
		long id = repository.create(new Pizza(0, "test2", BigDecimal.TEN, false));
		assertNotEquals(0, id);
		assertEquals(aantalPizzasVoorInsert + 1, this.countRowsInTable(PIZZAS));
		assertEquals(1, super.countRowsInTableWhere(PIZZAS, "id=" + id));
	}

	private long idVanTestPizza() { 
	return super.jdbcTemplate.queryForObject( 
	"select id from pizzas where naam='test'", Long.class);
	}

	private long idVanTest2Pizza() {
		return super.jdbcTemplate.queryForObject("select id from pizzas where naam='test2'", Long.class);
	}

	@Test
	public void delete() {
		long id = idVanTestPizza();
		int aantalPizzasVoorDelete = super.countRowsInTable(PIZZAS);
		repository.delete(id);
		assertEquals(aantalPizzasVoorDelete - 1, super.countRowsInTable(PIZZAS));
		assertEquals(0, super.countRowsInTableWhere(PIZZAS, "id=" + id));
	}

	@Test
	public void findById() {
		assertEquals("test", repository.findById(idVanTestPizza()).get().getNaam());
	}

	@Test
	public void findByOnbestaandeId() {
		assertFalse(repository.findById(-1).isPresent());
	}

	@Test
	public void update() {
		long id = idVanTestPizza();
		Pizza pizza = new Pizza(id, "test", BigDecimal.ONE, false);
		repository.update(pizza);
		assertEquals(0, BigDecimal.ONE.compareTo(
				super.jdbcTemplate.queryForObject("select prijs from pizzas where id=?", BigDecimal.class, id)));
	}

	@Test(expected = PizzaNietGevondenException.class)
	public void updateOnbestaandePizza() {
		repository.update(new Pizza(-1, "test", BigDecimal.ONE, false));
	}

	@Test
	public void findByPrijsBetween() {
		List<Pizza> pizzas = repository.findByPrijsBetween(BigDecimal.ONE, BigDecimal.TEN);
		assertEquals(super.countRowsInTableWhere(PIZZAS, "prijs between 1 and 10"), pizzas.size());
		pizzas.stream().map(pizza -> pizza.getPrijs()).reduce((vorigePrijs, prijs) -> {
			assertTrue(prijs.compareTo(BigDecimal.ONE) >= 0);
			assertTrue(prijs.compareTo(BigDecimal.TEN) <= 0);
			assertTrue(prijs.compareTo(vorigePrijs) >= 0);
			return prijs;
		});
	}

	@Test
	public void findUniekePrijzenGeeftPrijzenOplopend() {
		List<BigDecimal> prijzen = repository.findUniekePrijzen();
		long aantalPrijzen = super.jdbcTemplate.queryForObject("select count(distinct prijs) from pizzas", Long.class);
		assertEquals(aantalPrijzen, prijzen.size());
		prijzen.stream().reduce((vorigePrijs, prijs) -> {
			assertTrue(prijs.compareTo(vorigePrijs) > 0);
			return prijs;
		});
	}

	@Test
	public void findByPrijs() {
		List<Pizza> pizzas = repository.findByPrijs(BigDecimal.TEN);
		assertEquals(super.countRowsInTableWhere(PIZZAS, "prijs=10"), pizzas.size());
		pizzas.stream().map(pizza -> pizza.getNaam()).reduce((vorigeNaam, naam) -> {
			assertTrue(naam.compareTo(vorigeNaam) >= 0);
			return naam;
		});
	}

	@Test
	public void findByIds() {
		Set<Long> ids = new HashSet<>();
		long id1 = idVanTestPizza();
		long id2 = idVanTest2Pizza();
		ids.add(id1);
		ids.add(id2);
		List<Pizza> pizzas = repository.findByIds(ids);
		assertEquals(2, pizzas.size());
		assertEquals(id1, pizzas.get(0).getId());
		assertEquals(id2, pizzas.get(1).getId());
	}

	@Test
	public void findByIdsGeeftLegeVerzamelingPizzasBijLegeVerzamelingIds() {
		assertTrue(repository.findByIds(Collections.emptySet()).isEmpty());
	}

	@Test
	public void findByIdsGeeftLegeVerzamelingPizzasBijOnbestaandeIds() {
		assertTrue(repository.findByIds(Collections.singleton(-1L)).isEmpty());
	}

}
