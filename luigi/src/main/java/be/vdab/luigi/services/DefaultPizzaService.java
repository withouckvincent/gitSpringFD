package be.vdab.luigi.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.repositories.PizzaRepository;

@Transactional(readOnly = true , isolation = Isolation.READ_COMMITTED)
@Service
class DefaultPizzaService implements PizzaService {
	private final PizzaRepository pizzaRepository;

	DefaultPizzaService(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	@Override
	public long create(Pizza pizza) {
		return pizzaRepository.create(pizza);
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	@Override
	public void update(Pizza pizza) {
		pizzaRepository.update(pizza);
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	@Override
	public void delete(long id) {
		pizzaRepository.delete(id);
	}

	@Override
	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	@Override
	public Optional<Pizza> findById(long id) {
		return pizzaRepository.findById(id);
	}

	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		return pizzaRepository.findByPrijsBetween(van, tot);
	}

	@Override
	public long findAantalPizzas() {
		return pizzaRepository.findAantalPizzas();
	}

	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return pizzaRepository.findUniekePrijzen();
	}

	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return pizzaRepository.findByPrijs(prijs);
	}
}
