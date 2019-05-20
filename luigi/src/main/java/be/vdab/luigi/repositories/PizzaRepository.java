package be.vdab.luigi.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import be.vdab.luigi.domain.Pizza;

public interface PizzaRepository {
	long create(Pizza pizza); 
	void update(Pizza pizza); 
	void delete(long id); 
	List<Pizza> findAll(); 
	Optional<Pizza> findById(long id); 
	List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot); 
	long findAantalPizzas(); 
	List<BigDecimal> findUniekePrijzen(); 
	List<Pizza> findByPrijs(BigDecimal prijs); 
	List<Pizza> findByIds(Set<Long> ids);
}
