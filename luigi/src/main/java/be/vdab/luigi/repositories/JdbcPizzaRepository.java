package be.vdab.luigi.repositories;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.exceptions.PizzaNietGevondenException;

@Repository
public class JdbcPizzaRepository implements PizzaRepository {
	private final JdbcTemplate template;
	private final SimpleJdbcInsert insert;
	private final RowMapper<Pizza> pizzaMapper = (result, rowNum) -> new Pizza(result.getLong("id"),
			result.getString("naam"), result.getBigDecimal("prijs"), result.getBoolean("pikant"));
	private final RowMapper<BigDecimal> prijsMapper = (result, rowNum) -> result.getBigDecimal("prijs");

	/*
	 * JdbcPizzaRepository(JdbcTemplate template) { this.template = template; }
	 */

	JdbcPizzaRepository(JdbcTemplate template) {
		this.template = template;
		this.insert = new SimpleJdbcInsert(template);
		insert.withTableName("pizzas");
		insert.usingGeneratedKeyColumns("id");
	}

	@Override
	public long findAantalPizzas() {
		return template.queryForObject("select count(*) from pizzas", Long.class);
	}

	@Override
	public void delete(long id) {
		template.update("delete from pizzas where id=?", id);
	}

	@Override
	public void update(Pizza pizza) {
		String sql = "update pizzas set naam=?, prijs=?, pikant=? where id=?";
		if (template.update(sql, pizza.getNaam(), pizza.getPrijs(), pizza.isPikant(), pizza.getId()) == 0) {
			throw new PizzaNietGevondenException();
		}
	}

	@Override
	public long create(Pizza pizza) {
		Map<String, Object> kolomWaarden = new HashMap<>();
		kolomWaarden.put("naam", pizza.getNaam());
		kolomWaarden.put("prijs", pizza.getPrijs());
		kolomWaarden.put("pikant", pizza.isPikant());
		Number id = insert.executeAndReturnKey(kolomWaarden);
		return id.longValue();
	}

	@Override
	public List<Pizza> findAll() {
		String sql = "select id, naam, prijs, pikant from pizzas order by id";
		return template.query(sql, pizzaMapper);
	}

	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		String sql = "select id, naam, prijs, pikant from pizzas" + " where prijs between ? and ? order by prijs";
		;
		return template.query(sql, pizzaMapper, van, tot);
	}

	@Override
	public Optional<Pizza> findById(long id) {
		try {
			String sql = "select id, naam, prijs, pikant from pizzas where id=?";
			return Optional.of(template.queryForObject(sql, pizzaMapper, id));
		} catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}

	@Override
	public List<BigDecimal> findUniekePrijzen() {
		String sql = "select distinct prijs from pizzas order by prijs";
		return template.query(sql, prijsMapper);
	}

	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		String sql = "select id, naam, prijs, pikant from pizzas" + " where prijs=? order by naam";
		return template.query(sql, pizzaMapper, prijs);
	}

	@Override
	public List<Pizza> findByIds(Set<Long> ids) {
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		String sql = "select id, naam, prijs, pikant from pizzas where id in (";
		StringBuilder builder = new StringBuilder(sql);
		ids.forEach(id -> builder.append("?,"));
		builder.setCharAt(builder.length() - 1, ')');
		builder.append(" order by id");
		return template.query(builder.toString(), ids.toArray(), pizzaMapper);
	}

}
